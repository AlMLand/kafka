package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_COLOR
import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_DEFAULT
import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_DESCRIPTION
import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_ID
import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_NAME
import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_NODE_ID
import com.AlMLand.customConnectArchetype.schema.LabelField.LABEL_URL
import org.json.JSONArray
import org.json.JSONObject

class Label(
    val id: Long?,
    val nodeId: String?,
    val url: String?,
    val name: String?,
    val color: String?,
    val default: Boolean?,
    val description: String?,
    val optionalProperties: Map<String, Any>
) {
    companion object Utils {
        fun labelFromJson(json: JSONObject): Label =
            with(json) {
                Builder()
                    .id(getLong(LABEL_ID.value))
                    .nodeId(getString(LABEL_NODE_ID.value))
                    .url(getString(LABEL_URL.value))
                    .name(getString(LABEL_NAME.value))
                    .color(getString(LABEL_COLOR.value))
                    .default(getBoolean(LABEL_DEFAULT.value))
                    .description(getString(LABEL_DESCRIPTION.value))
                    .build()
            }

        fun labelFromJsonArray(json: JSONArray): List<Label?> = json.mapNotNull { labelFromJson(it as JSONObject) }

        data class Builder(
            private var id: Long? = null,
            private var nodeId: String? = null,
            private var url: String? = null,
            private var name: String? = null,
            private var color: String? = null,
            private var default: Boolean? = null,
            private var description: String? = null,
            private val optionalProperties: MutableMap<String, Any> = mutableMapOf()
        ) {
            fun id(id: Long) = apply { this.id = id }
            fun nodeId(nodeId: String) = apply { this.nodeId = nodeId }
            fun url(url: String) = apply { this.url = url }
            fun name(name: String) = apply { this.name = name }
            fun color(color: String) = apply { this.color = color }
            fun default(default: Boolean) = apply { this.default = default }
            fun description(description: String) = apply { this.description = description }
            fun optionalProperties(key: String, value: Any) = apply { this.optionalProperties[key] = value }
            fun build() = Label(id, nodeId, url, name, color, default, description, optionalProperties)
        }
    }
}