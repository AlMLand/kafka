package com.AlMLand.customConnectArchetype.sink

import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.common.config.ConfigDef

private const val MY_SETTING_CONFIG = "my.setting"
private const val MY_SETTING_DOC = "This is a setting important to my connector."

fun configDef(): ConfigDef =
    ConfigDef().define(
        ConfigKeyBuilder.of(MY_SETTING_CONFIG, ConfigDef.Type.STRING)
            .documentation(MY_SETTING_DOC)
            .importance(ConfigDef.Importance.HIGH)
            .build()
    )

class CustomSinkConnectorConfig(originals: MutableMap<*, *>) : AbstractConfig(configDef(), originals) {
    val customSetting: String = getString(MY_SETTING_CONFIG)
}
