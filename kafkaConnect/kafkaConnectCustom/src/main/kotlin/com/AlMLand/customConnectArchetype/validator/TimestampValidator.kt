package com.AlMLand.customConnectArchetype.validator

import com.AlMLand.customConnectArchetype.exceptions.ConfigDefValidatorException
import org.apache.kafka.common.config.ConfigDef
import java.time.Instant
import java.time.format.DateTimeParseException

class TimestampValidator : ConfigDef.Validator {
    override fun ensureValid(name: String?, value: Any?) {
        value?.let {
            try {
                Instant.parse(value as String)
            } catch (d: DateTimeParseException) {
                throw ConfigDefValidatorException("Wasn't able to parse the timestamp, make sure it is correct", d)
            }
        } ?: throw ConfigDefValidatorException("Timestamp must not be null")
    }
}
