package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_HTML_URL_FIELD
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_URL_FIELD
import org.json.JSONObject

class PullRequest private constructor(
    private val url: String?,
    private val htmlUrl: String?,
    private val request: String?,
    private val diffUrl: String?,
    private val mergedAt: String?,
    private val patchUrl: String?,
    private val optionalProperties: Map<String, Any>?
) {
    companion object Utils {
        fun pullRequestFromJson(json: JSONObject): PullRequest =
            with(json) {
                Builder()
                    .url(getString(PULL_REQUEST_URL_FIELD.value))
                    .htmlUrl(getString(PULL_REQUEST_HTML_URL_FIELD.value))
                    .build()
            }

        data class Builder(
            private var url: String? = null,
            private var htmlUrl: String? = null,
            private var request: String? = null,
            private var diffUrl: String? = null,
            private var mergedAt: String? = null,
            private var patchUrl: String? = null,
            private var optionalProperties: Map<String, Any>? = null
        ) {
            fun url(url: String) = apply { this.url = url }
            fun htmlUrl(htmlUrl: String) = apply { this.htmlUrl = htmlUrl }
            fun request(request: String) = apply { this.request = request }
            fun diffUrl(diffUrl: String) = apply { this.diffUrl = diffUrl }
            fun mergedAt(mergedAt: String) = apply { this.mergedAt = mergedAt }
            fun patchUrl(patchUrl: String) = apply { this.patchUrl = patchUrl }
            fun optionalProperties(optionalProperties: Map<String, Any>) =
                apply { this.optionalProperties = optionalProperties }

            fun build() = PullRequest(url, htmlUrl, request, diffUrl, mergedAt, patchUrl, optionalProperties)
        }
    }
}
