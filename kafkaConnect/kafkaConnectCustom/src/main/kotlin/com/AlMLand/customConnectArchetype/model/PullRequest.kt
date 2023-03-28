package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_DIFF_URL
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_HTML_URL
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_MERGED_AT
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_PATCH_URL
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_URL
import org.json.JSONException
import org.json.JSONObject

class PullRequest private constructor(
    val url: String?,
    val htmlUrl: String?,
    val diffUrl: String?,
    val patchUrl: String?,
    val mergedAt: String?,
    val optionalProperties: Map<String, Any>
) {
    companion object Utils {
        fun pullRequestFromJson(json: JSONObject): PullRequest =
            with(json) {
                Builder()
                    .url(getString(PULL_REQUEST_URL.value))
                    .htmlUrl(getString(PULL_REQUEST_HTML_URL.value))
                    .diffUrl(getString(PULL_REQUEST_DIFF_URL.value))
                    .patchUrl(getString(PULL_REQUEST_PATCH_URL.value))
                    .mergedAt(getStringOrNull(PULL_REQUEST_MERGED_AT.value))
                    .build()
            }

        private fun JSONObject.getStringOrNull(field: String): String? =
            try {
                getString(field)
            } catch (e: JSONException) {
                null
            }

        data class Builder(
            private var url: String? = null,
            private var htmlUrl: String? = null,
            private var diffUrl: String? = null,
            private var patchUrl: String? = null,
            private var mergedAt: String? = null,
            private val optionalProperties: MutableMap<String, Any> = mutableMapOf()
        ) {
            fun url(url: String) = apply { this.url = url }
            fun htmlUrl(htmlUrl: String) = apply { this.htmlUrl = htmlUrl }
            fun diffUrl(diffUrl: String) = apply { this.diffUrl = diffUrl }
            fun patchUrl(patchUrl: String) = apply { this.patchUrl = patchUrl }
            fun mergedAt(mergedAt: String?) = apply { this.mergedAt = mergedAt }
            fun optionalProperties(key: String, value: Any) = apply { this.optionalProperties[key] = value }

            fun build() = PullRequest(url, htmlUrl, diffUrl, patchUrl, mergedAt, optionalProperties)
        }
    }
}
