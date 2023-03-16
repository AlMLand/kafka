package com.AlMLand.customConnectArchetype.transformation

import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.common.config.ConfigDef.Importance.HIGH
import org.apache.kafka.common.config.ConfigDef.Type.STRING

private const val MY_SETTING_CONFIG = "my.setting"
private const val MY_SETTING_DOC = "This is a setting important to my connector."

fun configDef(): ConfigDef =
    ConfigDef().define(
        ConfigKeyBuilder.of(MY_SETTING_CONFIG, STRING)
            .documentation(MY_SETTING_DOC)
            .importance(HIGH)
            .build()
    )

class CustomKeyValueTransformationConfig(originals: MutableMap<*, *>) : AbstractConfig(configDef(), originals) {
    val customSetting: String = getString(MY_SETTING_CONFIG)
}