package com.AlMLand.kafka.wikimedia

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.RecordTooLargeException
import org.slf4j.Logger
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

internal fun sendTooLargeRecord(
    record: String,
    kafkaProducer: KafkaProducer<String, String>,
    topic: String,
    logger: Logger
) {
    kafkaProducer.send(ProducerRecord(topic, record)) { metadata, exception ->
        if (exception == null) logger.info(
            """
            Callback -> received new metadata: topic: {} | partition: {} | offset: {} | timestamp: {} | time: {}
            """.trimIndent(),
            metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp(),
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(metadata.timestamp()),
                TimeZone.getDefault().toZoneId()
            ).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
        )
        else when (exception) {
            is RecordTooLargeException -> {
                listOf(record.substring(0, middle(record)), record.substring(middle(record))).forEach {
                    sendTooLargeRecord(it, kafkaProducer, topic, logger)
                }
            }

            else -> logger.error("Error while producing", exception)
        }
    }.let {
        it.get().run {
            logger.info(
                """
                RecordMetadata -> send to topic: ${topic()} | partition: ${partition()} | offset: ${offset()}
                """.trimIndent()
            )
        }
    }
}

private fun middle(record: String) = record.length / 2