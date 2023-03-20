package com.AlMLand.customConnectArchetype.source

import com.github.jcustenborder.kafka.connect.utils.VersionUtil
import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.source.SourceTask

class CustomSourceTask : SourceTask() {
    override fun version(): String = VersionUtil.version(this::class.java)

    override fun start(props: MutableMap<String, String>) {
        TODO("Do things here that are required to start task. This could be open a connection to a database, etc")
    }

    override fun stop() {
        TODO("Do whatever is required to stop task")
    }

    override fun poll(): MutableList<SourceRecord> {
        TODO("Create SourceRecord objects that will be sent the kafka cluster")
    }
}
