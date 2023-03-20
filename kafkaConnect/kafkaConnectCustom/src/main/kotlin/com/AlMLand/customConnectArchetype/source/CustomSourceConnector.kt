package com.AlMLand.customConnectArchetype.source

import com.github.jcustenborder.kafka.connect.utils.VersionUtil
import com.github.jcustenborder.kafka.connect.utils.config.Description
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationImportant
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationNote
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationTip
import com.github.jcustenborder.kafka.connect.utils.config.Title
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.source.SourceConnector

@Description("This is a description of this connector and will show up in the documentation")
@DocumentationImportant("This is a important information that will show up in the documentation.")
@DocumentationTip("This is a tip that will show up in the documentation.")
@Title("Super Source Connector") //This is the display name that will show up in the documentation.
@DocumentationNote("This is a note that will show up in the documentation")
class CustomSourceConnector : SourceConnector() {

    private var customSourceConnectorConfig: CustomSourceConnectorConfig? = null

    override fun version(): String = VersionUtil.version(this::class.java)

    override fun start(props: MutableMap<String, String>) {
        customSourceConnectorConfig = CustomSourceConnectorConfig(props)
        TODO("Add things need to do to setup connector")
    }

    // Return task implementation
    override fun taskClass(): Class<out Task> {
        return CustomSourceTask::class.java
    }

    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> {
        TODO("Define the individual task configurations that will be executed")
    }

    override fun stop() {
        TODO("Do things that are necessary to stop connector")
    }

    override fun config(): ConfigDef = configDef()
}