package com.AlMLand.consumer.advanced

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import java.time.Duration.ofMillis
import java.util.*

/**
 * use case -> read specific messages from specific partitions
 * needed:
 * - remove the group.id from the consumer properties
 * - remove the subscription to the topic
 * - use consumer assign() and seek() APIs
 */
private val logger = LoggerFactory.getLogger(ConsumerSeekAssignDemo::class.java)
private const val OFFSETS_TO_READ_FROM = 7L
private const val NUMBER_OF_MESSAGES_TO_READ = 5
private var readMessages = 0

class ConsumerSeekAssignDemo

fun main() {
    KafkaConsumer<String, String>(consumerProperties).run {
        // assign and seek are mostly used to replay data or fetch a specific message
        TopicPartition("first_topic", 0).let { topicPartition ->
            assign(setOf(topicPartition))
            seek(topicPartition, OFFSETS_TO_READ_FROM)
            run messagesLimit@{
                while (true) {
                    poll(ofMillis(100)).let { records ->
                        records.forEach { record ->
                            if (++readMessages > NUMBER_OF_MESSAGES_TO_READ) return@messagesLimit
                            logger.info(
                                """
                            key: ${record.key()} | value: ${record.value()} | partition: ${record.partition()} | offset: ${record.offset()}
                            """.trimIndent()
                            )
                        }
                    }
                }
            }
        }
    }
    logger.info("exiting application...")
}

private val consumerProperties = Properties().apply {
    setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
    setProperty(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false")
}
