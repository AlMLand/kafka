package com.AlMLand.customConnectArchetype.source

import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.AUTH_PASSWORD_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.AUTH_USERNAME_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.BATCH_SIZE_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.OWNER_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.REPO_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.SINCE_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.TOPIC_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.AUTH_PASSWORD_DOC
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.AUTH_USERNAME_DOC
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.BATCH_SIZE_DOC
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.OWNER_DOC
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.REPO_DOC
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.SINCE_DOC
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorDocConstants.TOPIC_DOC
import com.AlMLand.customConnectArchetype.validator.BatchSizeValidator
import com.AlMLand.customConnectArchetype.validator.TimestampValidator
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.common.config.ConfigDef.Importance.HIGH
import org.apache.kafka.common.config.ConfigDef.Importance.LOW
import org.apache.kafka.common.config.ConfigDef.Type.INT
import org.apache.kafka.common.config.ConfigDef.Type.STRING
import java.time.Instant
import java.time.ZonedDateTime

class GithubSourceConnectorConfig(originals: MutableMap<String, String>, configDef: ConfigDef = configDef()) :
    AbstractConfig(configDef, originals) {
    companion object {
        fun configDef(): ConfigDef = ConfigDef()
            .define(TOPIC_CONFIG.value, STRING, HIGH, TOPIC_DOC.value)
            .define(OWNER_CONFIG.value, STRING, HIGH, OWNER_DOC.value)
            .define(REPO_CONFIG.value, STRING, HIGH, REPO_DOC.value)
            .define(BATCH_SIZE_CONFIG.value, INT, 100, BatchSizeValidator(), LOW, BATCH_SIZE_DOC.value)
            .define(
                SINCE_CONFIG.value, STRING, ZonedDateTime.now().minusYears(1).toInstant().toString(),
                TimestampValidator(), HIGH, SINCE_DOC.value
            )
            .define(AUTH_USERNAME_CONFIG.value, STRING, "", HIGH, AUTH_USERNAME_DOC.value)
            .define(AUTH_PASSWORD_CONFIG.value, STRING, "", HIGH, AUTH_PASSWORD_DOC.value)
    }

    fun ownerConfig(): String = getString(OWNER_CONFIG.value)
        ?: throw IllegalArgumentException("owner configuration cannot be null")

    fun repoConfig(): String = getString(REPO_CONFIG.value)
        ?: throw IllegalArgumentException("repository configuration cannot be null")

    fun batchSizeConfig(): Int = getInt(BATCH_SIZE_CONFIG.value)
        ?: throw IllegalArgumentException("batch size configuration cannot be null")

    fun sinceConfig(): Instant = Instant.parse(getString(SINCE_CONFIG.value))
        ?: throw IllegalArgumentException("since configuration cannot be null")

    fun topicConfig(): String = getString(TOPIC_CONFIG.value)
        ?: throw IllegalArgumentException("topic configuration cannot be null")

    fun authUsername(): String = getString(AUTH_USERNAME_CONFIG.value)
        ?: throw IllegalArgumentException("username configuration cannot be null")

    fun authPassword(): String = getString(AUTH_PASSWORD_CONFIG.value)
        ?: throw IllegalArgumentException("password configuration cannot be null")
}
