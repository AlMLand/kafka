package com.AlMLand.kafka.wikimedia

import com.launchdarkly.eventsource.EventSource
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.BUFFER_MEMORY_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.COMPRESSION_TYPE_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.MAX_BLOCK_MS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION
import org.apache.kafka.clients.producer.ProducerConfig.PARTITIONER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.RETRY_BACKOFF_MS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.internals.DefaultPartitioner
import org.apache.kafka.common.serialization.StringSerializer
import java.net.URI
import java.util.Properties
import java.util.concurrent.TimeUnit.MINUTES

class WikimediaChangesProducer

private const val BOOTSTRAP_SERVERS = "127.0.0.1:9092"
private const val TOPIC = "wikimedia.recentchange"
private const val URL = "https://stream.wikimedia.org/v2/stream/recentchange"

fun main() {
    KafkaProducer<String, String>(properties()).let {
        eventSource(it, TOPIC).start()
        MINUTES.sleep(10)
    }
}

private fun eventSource(kafkaProducer: KafkaProducer<String, String>, topic: String) =
    EventSource.Builder(WikimediaChangeHandler(kafkaProducer, topic), URI.create(URL)).build()

private fun properties() = Properties().apply {
    setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
    setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

    setProperty(
        PARTITIONER_CLASS_CONFIG,
        DefaultPartitioner::class.java.name
    ) // is deprecated => not use
    // if producer record key!=null => it es most likely preferred to not override the behavior of the partitioner, but is possible
    // if producer record key==null => RoundRobinPartitioner or Sticky Partitioner(improves performance of the producer especially when high throughput when the key is null)

// ######### ACKS #########
    setProperty(
        ACKS_CONFIG,
        "-1"
    ) // is same how "all" -> leader partition and all in sync replicas ("all" or "-1" is default since kafka 3.0)
    // setProperty(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2")   is good by producer acks=all and replication.factor=2 if partitions=3

// ######### RETRIES #########
    setProperty(RETRIES_CONFIG, "2147483647") // retries until delivery.timeout is reached , eto chislo Int.max()
    setProperty(DELIVERY_TIMEOUT_MS_CONFIG, "120000") // => fail after ~ 2 min
    setProperty(RETRY_BACKOFF_MS_CONFIG, "100") // cherez kakoj promezhutok powtorjat popitki otoslat

    setProperty(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5") // ensure max performance while keeping message ordering
    // up to 5 message batches being in flight(being sent beetwen the producer and the broker) at most => poluchaem parallelizm

// ######### IDEMPOTENCE #########
    setProperty(ENABLE_IDEMPOTENCE_CONFIG, "true") // raspoznaet record dublicati -> is default since kafka 3.0
    /*
    To prevent duplicates in Kafka introduced by the network.
        IDEMPOTENT KAFKA PRODUCER:
            example
            - Kafka producer sends a message to Kafka
            - The message was successfully written and replicated
            - Network issues prevented the broker acknowledgment from reaching the producer
            - The producer will treat the lack of acknowledgment as a temporary network issue and will retry sending the message (since it can’t know that it was received).
            - In that case, the broker will end up having the same message twice
    */

// ######### BATCHING MECHANISM #########
    setProperty(LINGER_MS_CONFIG, "0") // => wremja ozhidanija pered otprawkoj partii(batch)
    // how to long wait until send a batch. Adding a small number for example 5 ms helps add more messages in the batch at the expense of latency

    setProperty(BATCH_SIZE_CONFIG, "16384") // if batch is filled before 'linger.ms' - increase the batch size
    // default 16 KB, increasing a batch size to 32=(32*1024) KB or 64=(64*1024) KB can help increasing the compression, throughput and efficiently of request
    // if message is bigger that batch.size it will not be batched, it will send (budet otprawlen srazu zhe)
    // can monitore the average batch size metric using Kafka Producer Metrics

// ######### COMPRESSION #########
    setProperty(
        COMPRESSION_TYPE_CONFIG,
        "snappy"
    )
    // best practice => compression on producer site and broker config is compression.type=producer
    // can be enabled on producer level (snappy or lz4 is optimal speed) -> doesn't require any configuration change in the broker or consumer
    // compression type 'snyppy' is very good if messages are text based, example JSON, log lines
    // tak zhe compression mozhno dobawit na urowne broker or topic => it will consume extra CPU cycles
    // default for topic => compression.type=producer
    // esli v topic i v producer ustanovleni raznie compression types => togda to chto prihodit iz producer budet decompress i zatem compress s compression type, kotorij ukazan dlja topic

// ######## BUFFER MEMORY ########
    // if the producer bistree chem broker, t.e. broker ne w sostojanii obrabatiwat tak bistro records, to records are buffered on memory, esli nagruzka na broker umenschitsja,
    // to on smozhet obrabotat records iz buffer i 'buffer.memory' wnow opusteet
    // elsi 'buffer.memory' zapolnitsja do maksimuma, to method .send() zablokiruetsja
    setProperty(BUFFER_MEMORY_CONFIG, "33554432") // default 32 MB
    setProperty(
        MAX_BLOCK_MS_CONFIG,
        "60000"
    ) // <= default value, razreschaet blockirowat .send() method do 60 secund, esli posle etogo on wse esche zablokirowan => to exception
}

/*
========= HIGH THROUGHPUT PRODUCER =========
- compression.type
- linger.my
- batch.size
 */
