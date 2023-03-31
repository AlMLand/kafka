package com.AlMLand.customConnectArchetype.source

import com.AlMLand.customConnectArchetype.exceptions.NotImplementedException
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfig.Companion.configDef
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.source.SourceConnector

/*
    https://api.github.com/repos/kubernetes/kubernetes/issues
    https://docs.github.com/en/rest/issues/issues?apiVersion=2022-11-28#list-repository-issues
*/
class GithubSourceConnector : SourceConnector() {

    private lateinit var githubSourceConnectorConfig: GithubSourceConnectorConfig

    override fun start(properties: MutableMap<String, String>) {
        githubSourceConnectorConfig = GithubSourceConnectorConfig(properties)
    }

    // opredeljaju zadachi dlja wseh taskow, kakie hochu imet v kafka connector'e
    override fun taskConfigs(maxTasks: Int): MutableList<MutableMap<String, String>> =
        mutableListOf<MutableMap<String, String>>().apply {
            add(githubSourceConnectorConfig.originalsStrings())
        }

    override fun taskClass(): Class<out Task> = GithubSourceTask::class.java

    override fun version(): String = packageVersion(this::class.java)

    override fun config(): ConfigDef = configDef()

    override fun stop() = throw NotImplementedException("not implemented feature in ${this.javaClass.simpleName}")
}
