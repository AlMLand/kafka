package com.AlMLand.customConnectArchetype.source

import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfig.Companion.configDef
import com.github.jcustenborder.kafka.connect.utils.VersionUtil
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.source.SourceConnector

/*
    https://api.github.com/repos/kubernetes/kubernetes/issues
    https://docs.github.com/en/rest/issues/issues?apiVersion=2022-11-28#list-repository-issues
*/
class GithubSourceConnector : SourceConnector() {

    private var githubSourceConnectorConfig: GithubSourceConnectorConfig? = null

    override fun start(properties: MutableMap<String, String>) {
        githubSourceConnectorConfig = GithubSourceConnectorConfig(properties)
    }

    // opredeljaju zadachi dlja wseh taskow, kakie hochu imet v kafka connector'e
    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> =
        mutableListOf<MutableMap<String, String>>().apply {
            githubSourceConnectorConfig?.let {
                add(it.originalsStrings())
            }
        }

    override fun taskClass(): Class<out Task> = GithubSourceTask::class.java

    override fun version(): String = VersionUtil.version(this::class.java)

    override fun config(): ConfigDef = configDef()

    override fun stop() {
        TODO("what are necessary to/by stop connector, example -> close db")
    }
}
