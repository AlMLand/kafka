package com.AlMLand.customConnectArchetype.sink

import com.github.jcustenborder.kafka.connect.utils.VersionUtil
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.sink.SinkRecord
import org.apache.kafka.connect.sink.SinkTask
import org.slf4j.LoggerFactory

class CustomSinkTask : SinkTask() {

    private var customSinkConnectorConfig: CustomSinkConnectorConfig? = null

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun version(): String = VersionUtil.version(this::class.java)

    override fun start(props: MutableMap<String, String>) {
        customSinkConnectorConfig = CustomSinkConnectorConfig(props)
        TODO("Create resources like database or api connections here")
    }

    override fun flush(currentOffsets: MutableMap<TopicPartition, OffsetAndMetadata>) {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Close resources here")
    }

    override fun put(records: MutableCollection<SinkRecord>) {
        TODO("Not yet implemented")
    }
}
