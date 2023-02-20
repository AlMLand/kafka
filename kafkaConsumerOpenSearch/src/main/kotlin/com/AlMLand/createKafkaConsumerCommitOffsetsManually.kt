package com.AlMLand

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.CLIENT_RACK_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.FETCH_MAX_BYTES_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.FETCH_MIN_BYTES_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties

internal fun kafkaConsumerCommitOffsetsManually(): KafkaConsumer<String, String> = KafkaConsumer(Properties().apply {
    setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(
        AUTO_OFFSET_RESET_CONFIG,
        "latest"
    ) // earliest -> from the start of the logs, latest -> from the end of the logs, none -> throw exception if no offsets found
    setProperty(GROUP_ID_CONFIG, "consumer_opensearch")
    setProperty(ENABLE_AUTO_COMMIT_CONFIG, "false")
// #########################################################
    /*
     CONSUMER HEARTBEAT THREAD MECHANISM:
        heartbeat.interval.ms (default=3000 milliseconds)
        tell to kafka broker in intervall default=3000 milliseconds "consumeri schiwi, oni zdes i rabotajut", goog practice set to 1/3 of session.timeout.ms
    */
    setProperty(HEARTBEAT_INTERVAL_MS_CONFIG, "3000")

    /*
    CONSUMER HEARTBEAT THREAD MECHANISM:
        session.timeout.ms (dafault=45000 milliseconds)
        - if no heartbeat is send during the period, the consumer is considers dead
        - mozhno установить еще ниже для более быстрой перебалансировки потребителей
    */
    setProperty(SESSION_TIMEOUT_MS_CONFIG, "45000")
// #########################################################
    /*
    CONSUMER POLL THREAD MECHANISM:
        max.poll.interval.ms (default=300000 milliseconds) -> this is used to detect a data processing issue with the consumer
        - maximum amount of time between two .poll() calls before declaring the consumer dead, sledowatelno -> esli obrabotka poluchennih dannih
            dlitsja bolee 5 minut, broker pojmet, chto consumer umer
     */
    setProperty(MAX_POLL_INTERVAL_MS_CONFIG, "300000")
    /*
    CONSUMER POLL THREAD MECHANISM:
        max.poll.records (default=500)
        - controls how many records to receive per poll request
        - increase if messages are very small and have a lot of available RAM
        - good to monitor how many records are polled per request
        - decrease if it takes too much time to process records
     */
    setProperty(MAX_POLL_RECORDS_CONFIG, "500")
// #########################################################
    /*
    CONSUMER POLL BEHAVIOR:
        fetch.min.bytes (default=1 byte)
        - controls how much data want to pull at least on each request
        - helps improving throughput and decreasing request number
        - at the cost of latency
     */
    setProperty(FETCH_MIN_BYTES_CONFIG, "1")
    /*
    CONSUMER POLL BEHAVIOR:
        fetch.max.wait.ms (default=500 milliseconds)
        - the maximum amount of time the Kafka broker will block before answering the fetch request if there ins't sufficient data to
            immediately satisfy the requirement give by fetch.min.bytes
        - это означает, что до тех пор, пока не будет выполнено требование fetch.min.bytes,
            будет задержка до 500 мс перед получением данных consumer (например, введение потенциальной задержки для повышения эффективности запроса)
     */
    setProperty(FETCH_MAX_BYTES_CONFIG, "500")
    /*
    CONSUMER POLL BEHAVIOR:
        max.partition.fetch.bytes (default=1 MB) = advanced config, change only if consumer already has maxes out on throughput
        - the maximum amount of data per partition the server will return, primer: chtenii iz 100 partition oznachaet, chto nuzhno ne menee 100 MB operativnoj pamjati(RAM)
     */
    setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "1048576")
    /*
    CONSUMER POLL BEHAVIOR:
        fetch.max.bytes (default=52428800 bytes, ca. 55 MB) = advanced config, change only if consumer already has maxes out on throughput
        - max data returned for each fetch request
        - if have available memory, try increase fetch.max.bytes to allow the consumer to read more data in each request
     */
    setProperty(FETCH_MAX_BYTES_CONFIG, "52428800")
// #########################################################
    /*
    CONSUMER RACK AWARENESS:
    SINCE KAFKA 2.0.4 -> it is possible to configure consumers to read from the closest replicas(blizhajschih replic)
        this may help improve latency and also decrease network costs if using the cloud:
            broker settings:
                - rack.id (default="") -> config must be set to the data centre ID(example: AZ ID in AWS -> rack.id=usw2-az1)
                - replica.selector.class  -> must be set to org.apache.kafka.common.replica.RackAwareReplicaSelector
            consumer settings:
                - client.rack (default="") -> to the data centre ID the consumer is launched on
     */
    setProperty(CLIENT_RACK_CONFIG, "")
})

/*
ENABLE_AUTO_COMMIT=false:
pri etom podhode (setProperty(ENABLE_AUTO_COMMIT_CONFIG, "false")) neobhodimo vruchnuje commitit offsets v Kafka, prichem tolko posle uspeschnoj obrabotki poluchennih records
naprimer:
      consumer.commitAsync { offsets, exception ->
      if (exception == null) logger.info(
         """
         ${offsets.size} offsets habe been committed
      """.trimIndent()
      )
   }

REPLAYING DATA FROM TOPIC FOR A CONSUMER GROUP: -> wzjat powtorno dannie iz topic s consumer group, dannie offset kotorih uzhe pozadi aktuallnogo offseta
1. take all the consumers from a specific group
2. use kafka-consumers-groups command to set offset to what you want
3. restart consumer

OFFSET_RETENTION_MINUTES - config auf der Broker seite (good practice set to at least 1 month):
offsets.retention.minutes   ->    После того, как группа потребителей потеряет всех своих потребителей (т. е. станет пустой),
ее смещения будут сохраняться в течение этого периода хранения, прежде чем они будут удалены.
Для автономных потребителей (использующих ручное назначение) смещения будут истечены после времени последней фиксации плюс этот период хранения
 */
