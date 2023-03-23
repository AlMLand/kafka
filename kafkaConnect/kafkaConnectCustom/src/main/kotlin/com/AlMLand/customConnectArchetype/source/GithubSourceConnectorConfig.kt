package com.AlMLand.customConnectArchetype.source

import com.AlMLand.customConnectArchetype.validator.BatchSizeValidator
import com.AlMLand.customConnectArchetype.validator.TimestampValidator
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.common.config.ConfigDef.Importance.HIGH
import org.apache.kafka.common.config.ConfigDef.Importance.LOW
import org.apache.kafka.common.config.ConfigDef.Type.INT
import org.apache.kafka.common.config.ConfigDef.Type.STRING
import java.time.ZonedDateTime

class GithubSourceConnectorConfig(originals: MutableMap<String, String>, configDef: ConfigDef = configDef()) :
    AbstractConfig(configDef, originals) {
    companion object {
        private const val TOPIC_CONFIG = "topic"
        private const val TOPIC_DOC = "Topic to write to"
        private const val OWNER_CONFIG = "github.owner"
        private const val OWNER_DOC = "Owner of the repository you'd like to follow"
        private const val REPO_CONFIG = "github.repo"
        private const val REPO_DOC = "Repository you'd like to follow"
        private const val SINCE_CONFIG = "since.timestamp"
        private const val SINCE_DOC =
            """
                Only issues updated at or after this time are returned. 
                This is a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ. Defaults to a year from first launch.
            """
        private const val BATCH_SIZE_CONFIG = "batch.size"
        private const val BATCH_SIZE_DOC = "Number of data points to retrieve at a time. Defaults to 100 (max value)"
        private const val AUTH_USERNAME_CONFIG = "auth.username"
        private const val AUTH_USERNAME_DOC = "Optional Username to authenticate calls"
        private const val AUTH_PASSWORD_CONFIG = "auth.password"
        private const val AUTH_PASSWORD_DOC = "Optional Password to authenticate calls"

        fun configDef(): ConfigDef = ConfigDef()
            .define(TOPIC_CONFIG, STRING, HIGH, TOPIC_DOC)
            .define(OWNER_CONFIG, STRING, HIGH, OWNER_DOC)
            .define(REPO_CONFIG, STRING, HIGH, REPO_DOC)
            .define(BATCH_SIZE_CONFIG, INT, 100, BatchSizeValidator(), LOW, BATCH_SIZE_DOC)
            .define(
                SINCE_CONFIG, STRING, ZonedDateTime.now().minusYears(1).toInstant().toString(),
                TimestampValidator(), HIGH, SINCE_DOC
            )
            .define(AUTH_USERNAME_CONFIG, STRING, "", HIGH, AUTH_USERNAME_DOC)
            .define(AUTH_PASSWORD_CONFIG, STRING, "", HIGH, AUTH_PASSWORD_DOC)
    }
}
