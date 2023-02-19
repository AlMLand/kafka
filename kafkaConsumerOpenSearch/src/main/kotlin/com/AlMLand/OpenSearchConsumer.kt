package com.AlMLand

import com.google.gson.JsonParser
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.opensearch.OpenSearchStatusException
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
        private val logger = LoggerFactory.getLogger(this::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            restHighLevelClient().use { client ->
                createIndex(client, INDEX, logger)
                kafkaConsumer().use { consumer ->
                    consumer.subscribe(listOf(TOPIC))
                    kotlin.run stepLimit@{
                        while (true) {
                            consumer.poll(Duration.ofMillis(3000)).let { records ->
                                logger.info("received record count: ${records.count()}")
                                records.forEach { record ->
                                    putToIndex(client, record)
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun putToIndex(client: RestHighLevelClient, record: ConsumerRecord<String, String>) {
            // idempotent strategy 1 -> define an ID using Kafka Record coordinates
            // das kann man z.B. in der DB speichern und schauen, ob ich das schon bekommen habe
            // val id = "${record.topic()}_${record.partition()}_${record.offset()}"

            // idempotent strategy 2 (preferred) -> extract ID from value (z.B JSON was man als value bekommt)
            val id = extractID(record.value())

            try {
                client.index(
                    IndexRequest(INDEX).source(record.value(), XContentType.JSON).id(id),
                    RequestOptions.DEFAULT
                ).also {
                    logger.info("insert document with id: ${it.id} into opensearch index: ${it.index}")
                }
            } catch (e: Exception) {
                when (e) {
                    is OpenSearchStatusException -> logger.error("open search status exception", e)

                    else -> logger.error("surprise exception", e)
                }
            }
        }

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
    }
}
