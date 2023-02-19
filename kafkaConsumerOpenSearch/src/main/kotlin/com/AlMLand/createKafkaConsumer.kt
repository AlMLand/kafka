package com.AlMLand

import org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties

internal fun kafkaConsumer(): KafkaConsumer<String, String> = KafkaConsumer(Properties().apply {
    setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    setProperty(AUTO_OFFSET_RESET_CONFIG, "latest")
    setProperty(GROUP_ID_CONFIG, "consumer_opensearch")
})

/*
DELIVERY SEMANTICS:
- at most once -> offset are committed as soon as the message is received
- at least once (preferred) -> offsets are committed after the message is processed. If processing goes
    wrong, the message will read again. This can result in duplicate processing of message. Make sure processing is IDEMPOTENT.
- exactly once -> can be achieved for Kafka=>Kafka workflow using the Transactional API(Kafka Stream API). For Kafka=>Sink workflows,
    use an idempotent consumer.

CONSUMER OFFSETS DELIVERY SEMANTICS:
- strategy 1 - auto offset commit behavior -> (easy) enable.auto.commit=true & synchronous processing of batches,
                pri auto.commit.interval=5000(default) - kazhdie 5 secund offsets budut committed
                wazhno udostoweritsja do sledujuschego wizowa methoda `poll()`, chto tolko poluchennie records bili uspeschno obrabotanni,
                i tolko posle etogo wnow wiziwat method `poll()`.
- strategy 2 - disabled auto offset commit behavior -> (medium) enable.auto.commit=false & manual commit of offsets
                time-to-time call `commitSync()` or `commitAsync()` with correct offsets manually.
                Eta strategija nuzhna naprimer esli dannie iz Kafka t.e. batches dolzhni nakopitja i potom obrabotatsja za odin raz,
                    naprimer: za odin raz sohranit wse v DB
- strategy 3 - disabled auto offset commit behavior -> (advanced) enable.auto.commit=false & storing offsets externally
                neobhodimo nachinat chitat s opredelennih mest, v etom pomozhet method `seek()` i sohranjat offsets v DB naprimer
                need to handle the cases where rebalancing happen (ConsumerRebalanceListener interface).
                Idempotent zdes znachit: data processing + commit offsets(sohranenie v DB naprimer) - as part of a single transaction

IDEMPOTENT strategy:
- strategy 1 -> define an ID using Kafka Record coordinates
    example: val id = "${record.topic()}_${record.partition()}_${record.offset()}"
    das kann man z.B. in der DB speichern und schauen, ob ich das schon bekommen habe oder z.B ElasticSearch srazu obnovit uzhe imejuschijsja element s etim ID
- strategy 2 (preferred) -> extract ID from value (z.B JSON was man als value bekommt)
    example:        private fun extractID(json: String): String {
                        // gson library
                        return JsonParser.parseString(json)
                            .asJsonObject.get("meta")
                            .asJsonObject.get("id")
                            .asString
                    }
    das kann man z.B. in der DB speichern und schauen, ob ich das schon bekommen habe oder z.B ElasticSearch srazu obnovit uzhe imejuschijsja element s etim ID
 */
