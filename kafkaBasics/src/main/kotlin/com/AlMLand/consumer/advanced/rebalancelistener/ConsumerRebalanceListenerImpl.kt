package com.AlMLand.consumer.advanced.rebalancelistener

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory

/**
 * User cases:
 * - saving offsets in a custom store
 * - flushing out any kind of cache of intermediate results the consumer may be keeping
 */
class ConsumerRebalanceListenerImpl(private val kafkaConsumer: KafkaConsumer<String, String>) :
    ConsumerRebalanceListener {

    val currentOffsets = mutableMapOf<TopicPartition, OffsetAndMetadata>()

    override fun onPartitionsRevoked(partitions: MutableCollection<TopicPartition>?) {
        logger.info("onPartitionsRevoked callback triggered")
        logger.info("committing offsets: $currentOffsets")
        kafkaConsumer.commitSync(currentOffsets)
    }

    override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>?) {
        logger.info("onPartitionsAssigned callback triggered")
    }

    fun addOffsetsToTrack(topic: String, partition: Int, offset: Long) {
        currentOffsets[TopicPartition(topic, partition)] = OffsetAndMetadata(offset + 1, null)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
