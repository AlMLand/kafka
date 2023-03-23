package com.AlMLand.customConnectArchetype.validator

import com.AlMLand.customConnectArchetype.exceptions.ConfigDefValidatorException
import org.apache.kafka.common.config.ConfigDef

class BatchSizeValidator : ConfigDef.Validator {
    override fun ensureValid(name: String?, value: Any?) {
        value?.let {
            with(it as Int) {
                if (0 > this || this > 100)
                    throw ConfigDefValidatorException(
                        """
                            Batch size must be a positive integer that's less or equal to 100
                        """.trimIndent()
                    )
            }
        } ?: throw ConfigDefValidatorException("Batch size must not be null")
    }
}
