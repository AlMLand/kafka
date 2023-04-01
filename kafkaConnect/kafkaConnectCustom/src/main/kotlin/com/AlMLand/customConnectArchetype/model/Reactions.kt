package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_CONFUSED
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_EYES
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_HEART
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_HOORAY
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_LAUGH
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_MINUS_ONE
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_PLUS_ONE
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_ROCKET
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_TOTAL_COUNT
import com.AlMLand.customConnectArchetype.schema.ReactionsField.REACTIONS_URL
import org.json.JSONObject

class Reactions private constructor(
    val url: String?,
    val totalCount: Int?,
    val plusOne: Int?,
    val minusOne: Int?,
    val laugh: Int?,
    val hooray: Int?,
    val confused: Int?,
    val heart: Int?,
    val rocket: Int?,
    val eyes: Int?,
    val optionalProperties: Map<String, Any>
) {
    companion object Utils {
        fun reactionsFromJson(json: JSONObject): Reactions =
            with(json) {
                Builder()
                    .url(getString(REACTIONS_URL.value))
                    .totalCount(getInt(REACTIONS_TOTAL_COUNT.value))
                    .plusOne(getInt(REACTIONS_PLUS_ONE.value))
                    .minusOne(getInt(REACTIONS_MINUS_ONE.value))
                    .laugh(getInt(REACTIONS_LAUGH.value))
                    .hooray(getInt(REACTIONS_HOORAY.value))
                    .confused(getInt(REACTIONS_CONFUSED.value))
                    .heart(getInt(REACTIONS_HEART.value))
                    .rocket(getInt(REACTIONS_ROCKET.value))
                    .eyes(getInt(REACTIONS_EYES.value))
                    .build()
            }

        data class Builder(
            private var url: String? = null,
            private var totalCount: Int? = null,
            private var plusOne: Int? = null,
            private var minusOne: Int? = null,
            private var laugh: Int? = null,
            private var hooray: Int? = null,
            private var confused: Int? = null,
            private var heart: Int? = null,
            private var rocket: Int? = null,
            private var eyes: Int? = null,
            private val optionalProperties: MutableMap<String, Any> = mutableMapOf()
        ) {
            fun url(url: String) = apply { this.url = url }
            fun totalCount(totalCount: Int) = apply { this.totalCount = totalCount }
            fun plusOne(plusOne: Int) = apply { this.plusOne = plusOne }
            fun minusOne(minusOne: Int) = apply { this.minusOne = minusOne }
            fun laugh(laugh: Int) = apply { this.laugh = laugh }
            fun hooray(hooray: Int) = apply { this.hooray = hooray }
            fun confused(confused: Int) = apply { this.confused = confused }
            fun heart(heart: Int) = apply { this.heart = heart }
            fun rocket(rocket: Int) = apply { this.rocket = rocket }
            fun eyes(eyes: Int) = apply { this.eyes = eyes }
            fun optionalProperties(key: String, value: Any) = apply { this.optionalProperties[key] = value }

            fun build() = Reactions(
                url, totalCount, plusOne, minusOne, laugh, hooray, confused, heart, rocket,
                eyes, optionalProperties
            )
        }
    }
}