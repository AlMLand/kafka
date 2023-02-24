package com.AlMLand.kafkaConnector

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.source.SourceConnector

class WikimediaConnector(
    private var topic: String?,
    private var uri: String?,
    private var reconnectDuration: String?
) : SourceConnector() {

    companion object {
        //static version for the connector
        var VERSION = "0.01"

        // define configs name
        const val TOPIC_CONFIG = "topic"
        const val URL_CONFIG = "url"
        const val RECONNECT_TIME_CONFIG = "reconnect.duration"

        //config definition object
        private val CONFIG_DEF = ConfigDef()
            .define(
                TOPIC_CONFIG,
                ConfigDef.Type.STRING,
                null,
                ConfigDef.Importance.HIGH,
                "The topic to publish data to"
            )
            .define(
                URL_CONFIG,
                ConfigDef.Type.STRING,
                null,
                ConfigDef.Importance.HIGH,
                "The event stream url to fetch events from"
            )
            .define(
                RECONNECT_TIME_CONFIG,
                ConfigDef.Type.INT,
                null,
                ConfigDef.Importance.LOW,
                "reconnect duration (milliseconds) config for event stream url. defaults to 3 seconds"
            )
    }

    override fun start(map: Map<String?, String?>) {
        // init the configs on start
        topic = map[TOPIC_CONFIG]
        uri = map[URL_CONFIG]
        reconnectDuration = map.getOrDefault(RECONNECT_TIME_CONFIG, "3000")
    }

    override fun taskClass(): Class<WikimediaTask> = WikimediaTask::class.java

    override fun taskConfigs(i: Int): List<Map<String, String?>> =
        listOf<Map<String, String?>>(mutableMapOf<String, String?>().apply {
            this[TOPIC_CONFIG] = topic
            this[URL_CONFIG] = uri
            this[RECONNECT_TIME_CONFIG] = reconnectDuration
        })

    override fun config(): ConfigDef = CONFIG_DEF

    override fun version(): String = VERSION

    override fun stop() {}
}
