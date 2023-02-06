package com.AlMLand

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.*

private val logger = LoggerFactory.getLogger(ProducerDemoKeys::class.java)

class ProducerDemoKeys

fun main() {
    KafkaProducer<String, String>(Properties().apply {
        setProperty("bootstrap.servers", "127.0.0.1:9092") // or "localhost:9092"
        setProperty("key.serializer", StringSerializer::class.java.name)
        setProperty("value.serializer", StringSerializer::class.java.name)
    }).run {
        repeat(3) {
            for (index in 1..10)
                send(ProducerRecord("first_topic", "id_$index", "my_test_record nr: $index")) { metadata, exception ->
                    if (exception == null)
                        logger.info(
                            """
                    Received new metadata:
                    key: id_${index} | partition: ${metadata.partition()} | offset: ${metadata.offset()}
                """.trimIndent()
                        )
                    else
                        logger.error("Error while producing", exception)
                }
        }
        flush()
        close()
    }
}
