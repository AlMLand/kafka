package com.AlMLand.consumer.advanced.rebalancelistener

import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import java.time.Duration.ofMillis
import java.util.*

class ConsumerRebalanceListenerDemo

private val logger = LoggerFactory.getLogger(ConsumerRebalanceListenerDemo::class.java)

fun main() {
    KafkaConsumer<String, String>(consumerProperties).run {
        registerWakeupException()
        ConsumerRebalanceListenerImpl(this).let { rebalanceListener ->
            try {
                subscribe(listOf("first_topic"), rebalanceListener)
                while (true) {
                    poll(ofMillis(1000)).let { records ->
                        records.forEach { record ->
                            logConsumerRecordData(record)
                            rebalanceListener.addOffsetsToTrack(record.topic(), record.partition(), record.offset())
                        }
                    }
                    commitAsync() // commitAsync as have processed all data -> don't want to block until the next .poll() call
                }
            } catch (we: WakeupException) {
                logger.info("consumer is starting to shut down")
            } catch (e: Exception) {
                logger.error("surprise exception", e)
            } finally {
                try {
                    commitSync(rebalanceListener.currentOffsets) // must commit the offsets synchronously here
                } finally {
                    closeConsumer()
                }
            }
        }
    }
}

private fun logConsumerRecordData(record: ConsumerRecord<String, String>) {
    logger.info(
        """
            Key: ${record.key()} | value: ${record.value()} | partition: ${record.partition()} | offset: ${record.offset()}
        """.trimIndent()
    )
}

private fun KafkaConsumer<String, String>.closeConsumer() {
    close()
    logger.info(
        """
            gracefully shut down -> close consumer and commit offsets to the kafka __consumer_offsets topic
        """.trimIndent()
    )
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

private val consumerProperties = Properties().apply {
    setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(GROUP_ID_CONFIG, "first_consumer_group")
    setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
    setProperty(ENABLE_AUTO_COMMIT_CONFIG, "false")
    setProperty(ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false")
    setProperty(PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor::class.java.name)
}
