package com.AlMLand

import com.google.gson.JsonParser
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.opensearch.action.bulk.BulkRequest
import org.opensearch.action.index.IndexRequest
import org.opensearch.client.RequestOptions
import org.opensearch.client.RestHighLevelClient
import org.opensearch.common.xcontent.XContentType
import org.slf4j.LoggerFactory
import java.time.Duration

internal class OpenSearchConsumer {
    companion object {
        private const val INDEX = "wikimedia"
        private const val TOPIC = "wikimedia.recentchange"
        private const val WIKIMEDIA_EVENT_MESSAGE_JSON_START = 15
        private const val WIKIMEDIA_EVENT_MESSAGE = "event: message"
        private val logger = LoggerFactory.getLogger(this::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            restHighLevelClient().use { client ->
                createIndex(client, INDEX, logger)
                kafkaConsumerCommitOffsetsManually().use { consumer ->
                    consumer.registerWakeupException()
                    try {
                        consumer.subscribe(listOf(TOPIC))
                        kotlin.run stepLimit@{
                            while (true) {
                                consumer.poll(Duration.ofMillis(3000)).let { records ->
                                    logger.info("received record count: ${records.count()}")
                                    // open search no bulk request
                                    // putToIndex(records, client)

                                    // open search bulk request
                                    putToIndexBulk(records, client, consumer)
                                }
                            }
                        }
                    } catch (we: WakeupException) {
                        logger.info("consumer is starting to shut down")
                    } catch (e: Exception) {
                        logger.error("surprise exception", e)
                    } finally {
                        consumer.close()
                        client.close()
                        logger.info(
                            """
                                gracefully shut down -> close consumer and commit offsets to the kafka __consumer_offsets topic
                            """.trimIndent()
                        )
                    }
                }
            }
        }

        private fun putToIndexBulk(
            records: ConsumerRecords<String, String>,
            client: RestHighLevelClient,
            consumer: KafkaConsumer<String, String>
        ) {
            BulkRequest().apply {
                records.forEach { record ->
                    handleWikiEventMessages(record.value()).let {
                        add(
                            IndexRequest(INDEX).source(it, XContentType.JSON)
                                .id(extractID(it))
                        )
                    }
                }
            }.let { request ->
                if (request.numberOfActions() > 0) client.bulk(request, RequestOptions.DEFAULT).also {
                    logger.info("inserted records: ${it.items.size}")
                    // if  enable.auto.commit=false  ;  kafkaConsumerCommitOffsetsManually()
                    manuallyOffsetsCommitToBroker(records, consumer)
                }
            }
        }

        private fun manuallyOffsetsCommitToBroker(
            records: ConsumerRecords<String, String>,
            consumer: KafkaConsumer<String, String>
        ) {
            if (records.count() > 0) consumer.commitSync().also {
                logger.info("offsets have been synchronous committed")
            }
            //     .commitAsync { offsets, exception ->
            //     if (exception == null) {
            //         offsets.entries.map {
            //             Triple(
            //                 it.value.offset(),
            //                 it.key.topic(),
            //                 it.key.partition()
            //             )
            //         }.forEach {
            //             logger.info(
            //                 """
            //                     OFFSET: ${it.first} have been asynchronous committed to TOPIC: ${it.second}, PARTITION: ${it.third}.
            //                 """.trimIndent()
            //             )
            //         }
            //     }
            // }
        }

        private fun putToIndex(records: ConsumerRecords<String, String>, client: RestHighLevelClient) {
            // idempotent strategy 1 -> define an ID using Kafka Record coordinates
            // das kann man z.B. in der DB speichern und schauen, ob ich das schon bekommen habe
            // val id = "${record.topic()}_${record.partition()}_${record.offset()}"

            records.forEach { record ->
                handleWikiEventMessages(record.value()).let { json ->
                    client.index(
                        IndexRequest(INDEX).source(json, XContentType.JSON)
                            .id(extractID(json)), // idempotent strategy 2 (preferred) -> extract ID from value (z.B JSON was man als value bekommt)
                        RequestOptions.DEFAULT
                    ).also {
                        logger.info("insert document with id: ${it.id} into opensearch index: ${it.index}")
                    }
                }
            }
        }

        private fun handleWikiEventMessages(json: String?): String? =
            if (isWikiEventMessage(json)) json?.substring(WIKIMEDIA_EVENT_MESSAGE_JSON_START)
            else json

        private fun isWikiEventMessage(json: String?) = json?.contains(WIKIMEDIA_EVENT_MESSAGE) == true

        private fun extractID(json: String?): String {
            // google gson library
            return """
                ${
                JsonParser.parseString(json)
                    .asJsonObject.get("meta")
                    .asJsonObject.get("id")
                    .asString
            }_${
                JsonParser.parseString(json)
                    .asJsonObject.get("id")
                    ?.asString ?: "default"
            }_${
                JsonParser.parseString(json)
                    .asJsonObject.get("user")
                    ?.asString ?: "default"
            }
            """.trimIndent()
        }

        private fun KafkaConsumer<String, String>.registerWakeupException() {
            Thread.currentThread().let {
                Runtime.getRuntime().addShutdownHook(Thread() {
                    logger.info("detected a shutdown, let's exit by calling consumer.wakeup()...")
                    wakeup()
                    try {
                        it.join()
                    } catch (ie: InterruptedException) {
                        ie.printStackTrace()
                    }
                })
            }
        }
    }
}
