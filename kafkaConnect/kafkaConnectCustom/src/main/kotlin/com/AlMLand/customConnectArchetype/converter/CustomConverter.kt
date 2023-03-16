package com.AlMLand.customConnectArchetype.converter

import com.AlMLand.customConnectArchetype.exceptions.NotImplementedException
import com.github.jcustenborder.kafka.connect.utils.config.Description
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationImportant
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationNote
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationTip
import com.github.jcustenborder.kafka.connect.utils.config.Title
import org.apache.kafka.common.header.Headers
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.data.SchemaAndValue
import org.apache.kafka.connect.storage.Converter
import org.slf4j.LoggerFactory

@DocumentationTip("This is a tip that will show up in the documentation.")
@DocumentationNote("This is a note that will show up in the documentation")
@Title("Super Converter") //This is the display name that will show up in the documentation.
@Description("This is a description of this connector and will show up in the documentation")
@DocumentationImportant("This is a important information that will show up in the documentation.")
class CustomConverter : Converter {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun configure(configs: MutableMap<String, *>, isKey: Boolean) {
        TODO("Not yet implemented")
    }

    override fun toConnectData(topic: String, value: ByteArray): SchemaAndValue {
        throw NotImplementedException("This needs to be completed")
    }

    override fun fromConnectData(topic: String, schema: Schema, value: Any): ByteArray {
        throw NotImplementedException("This needs to be completed")
    }

    override fun toConnectData(topic: String, headers: Headers, value: ByteArray): SchemaAndValue {
        return super.toConnectData(topic, headers, value)
    }

    override fun fromConnectData(topic: String, headers: Headers, schema: Schema, value: Any): ByteArray {
        return super.fromConnectData(topic, headers, schema, value)
    }
}
