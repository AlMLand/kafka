package com.AlMLand

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*

private val logger = LoggerFactory.getLogger(ConsumerDemoWithShutdown::class.java)

class ConsumerDemoWithShutdown

fun main() {
    KafkaConsumer<String, String>(Properties().apply {
        setProperty("bootstrap.servers", "127.0.0.1:9092") // or "localhost:9092"
        setProperty("key.deserializer", StringDeserializer::class.java.name)
        setProperty("value.deserializer", StringDeserializer::class.java.name)
        setProperty("allow.auto.create.topics", "false")
        setProperty("group.id", "my_application")
        setProperty(
            "auto.offset.reset",
            "earliest"
        ) // none(wenn keine gruppe is da -> fail, restart app) | earliest | latest
    }).run {
        registerWakeupException()
        try {
            subscribe(listOf("first_topic"))
            while (true) {
                logger.info("polling")
                // tolko elsi kafka ne imeet messages, togta zhdem eto wremja
                poll(Duration.ofMillis(1000)).let { records ->
                    records.forEach {
                        logger.info(
                            """
                        Key: ${it.key()} | value: ${it.value()} | partition: ${it.partition()} | offset: ${it.offset()}
                    """.trimIndent()
                        )
                    }
                }
            }
        } catch (we: WakeupException) {
            logger.info("consumer is starting to shut down")
        } catch (e: Exception) {
            logger.error("surprise exception", e)
        } finally {
            close()
            logger.info(
                """
                gracefully shut down -> close consumer and commit offsets to the kafka __consumer_offsets topic
            """.trimIndent()
            )
        }
    }
}

private fun KafkaConsumer<String, String>.registerWakeupException() {
    Thread.currentThread().let {
        Runtime.getRuntime().addShutdownHook(Thread() {
            logger.info("detected a shutdown, let's exit by calling consumer.wakeup()...")
            wakeup()
            try {
                it.join()
            } catch (ie: InterruptedException) {
                ie.printStackTrace()
            }
        })
    }
}
