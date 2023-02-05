package com.AlMLand

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RoundRobinPartitioner
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private val logger = LoggerFactory.getLogger(ProducerDemoWithCallback::class.java)

class ProducerDemoWithCallback

fun main() {
    KafkaProducer<String, String>(Properties().apply {
        setProperty("bootstrap.servers", "127.0.0.1:9092") // or "localhost:9092"
        setProperty("key.serializer", StringSerializer::class.java.name)
        setProperty("value.serializer", StringSerializer::class.java.name)
        setProperty(
            "batch.size",
            "400"
        ) // so wenig nicht für production, nur um zu sehen: die unterschiede zwischen roundrobin und stickypartitioner
        setProperty(
            "partitioner.class",
            RoundRobinPartitioner::class.java.name
        ) // nicht für production, nur um zu sehen: die unterschiede zwischen roundrobin und stickypartitioner
    }).run {
        repeat(10) {
            for (index in 1..30)
                send(ProducerRecord("own_topic", "my_test_record nr: $index")) { metadata, exception ->
                    if (exception == null)
                        logger.info(
                            """
                    Received new metadata:
                    topic: ${metadata.topic()}
                    partition: ${metadata.partition()}
                    offset: ${metadata.offset()}
                    timestamp: ${metadata.timestamp()}
                    time: ${
                                LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(metadata.timestamp()),
                                    TimeZone.getDefault().toZoneId()
                                ).let {
                                    it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                                }
                            }
                """.trimIndent()
                        )
                    else
                        logger.error("Error while producing", exception)
                }
            Thread.sleep(500)
        }
        flush()
        close()
    }
}
