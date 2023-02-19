package com.AlMLand

import org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties

internal fun kafkaConsumerCommitOffsetsManually(): KafkaConsumer<String, String> = KafkaConsumer(Properties().apply {
    setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(AUTO_OFFSET_RESET_CONFIG, "latest")
    setProperty(GROUP_ID_CONFIG, "consumer_opensearch")
    setProperty(ENABLE_AUTO_COMMIT_CONFIG, "false")
})

/*
pri etom podhode neobhodimo vruchnuje commitit offsets v Kafka, prichem tolko posle uspeschnoj obrabotki poluchennih records
naprimer:
      consumer.commitAsync { offsets, exception ->
      if (exception == null) logger.info(
         """
         ${offsets.size} offsets habe been committed
      """.trimIndent()
      )
   }
 */
