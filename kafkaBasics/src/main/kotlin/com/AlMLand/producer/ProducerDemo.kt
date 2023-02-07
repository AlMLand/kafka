package com.AlMLand.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.*

private val logger = LoggerFactory.getLogger(ProducerDemo::class.java)

class ProducerDemo

fun main() {
    KafkaProducer<String, String>(Properties().apply {
        setProperty("bootstrap.servers", "127.0.0.1:9092") // or "localhost:9092"
        setProperty("key.serializer", StringSerializer::class.java.name)
        setProperty("value.serializer", StringSerializer::class.java.name)
    }).run {
        send(ProducerRecord("first_topic", "my_test_record"))
        flush()
        close()
    }
}
