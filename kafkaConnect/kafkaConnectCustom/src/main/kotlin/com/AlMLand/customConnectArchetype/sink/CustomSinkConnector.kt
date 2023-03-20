package com.AlMLand.customConnectArchetype.sink

import com.github.jcustenborder.kafka.connect.utils.VersionUtil
import com.github.jcustenborder.kafka.connect.utils.config.Description
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationImportant
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationNote
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationTip
import com.github.jcustenborder.kafka.connect.utils.config.Title
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.sink.SinkConnector
import org.slf4j.LoggerFactory

@Description("This is a description of this connector and will show up in the documentation")
@DocumentationImportant("This is a important information that will show up in the documentation.")
@DocumentationTip("This is a tip that will show up in the documentation.")
@Title("Super Sink Connector") //This is the display name that will show up in the documentation.
@DocumentationNote("This is a note that will show up in the documentation")
class CustomSinkConnector : SinkConnector() {

    private var customSinkConnectorConfig: CustomSinkConnectorConfig? = null

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun start(props: MutableMap<String, String>) {
        customSinkConnectorConfig = CustomSinkConnectorConfig(props)
        TODO("Add things need to do to setup connector")
        /**
         * This will be executed once per connector. This can be used to handle connector level setup. For
         * example if are persisting state, can use this to method to create state table.
         * Could also use this to verify permissions
         */
    }

    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> {
        TODO("Define the individual task configurations that will be executed")
        /**
         * This is used to schedule the number of tasks that will be running. This should not exceed maxTasks.
         * Here is a spot where can dish out work. For example if are reading from multiple tables
         * in a database, can assign a table per task.
         */
    }

    override fun version(): String = VersionUtil.version(this::class.java)

    override fun taskClass(): Class<out Task> = CustomSinkTask::class.java

    override fun config(): ConfigDef = configDef()

    override fun stop() {
        TODO("Do things that are necessary to stop connector")
    }
}
