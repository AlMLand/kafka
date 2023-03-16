package com.AlMLand.customConnectArchetype.transformation

import com.github.jcustenborder.kafka.connect.utils.config.Description
import com.github.jcustenborder.kafka.connect.utils.config.Title
import com.github.jcustenborder.kafka.connect.utils.transformation.BaseKeyValueTransformation
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.ConnectRecord
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.data.SchemaAndValue

@Title("Super Cool Transformation")
@Description("This transformation will change one record to another record.")
open class CustomKeyValueTransformation<R : ConnectRecord<R>>(isKey: Boolean) : BaseKeyValueTransformation<R>(isKey) {

    private var customKeyValueTransformationConfig: CustomKeyValueTransformationConfig? = null

    override fun configure(configs: MutableMap<String, *>) {
        customKeyValueTransformationConfig = CustomKeyValueTransformationConfig(configs)
    }

    override fun config(): ConfigDef = configDef()

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun processString(record: R, inputSchema: Schema, input: String): SchemaAndValue {
        return super.processString(record, inputSchema, input)
    }

    override fun processBytes(record: R, inputSchema: Schema, input: ByteArray): SchemaAndValue {
        return super.processBytes(record, inputSchema, input)
    }

    /**
     * This implementation works against the key of the record.
     */
    class Key<R : ConnectRecord<R>> : CustomKeyValueTransformation<R>(true)

    /**
     * This implementation works against the value of the record.
     */
    class Value<R : ConnectRecord<R>> : CustomKeyValueTransformation<R>(false)
}
