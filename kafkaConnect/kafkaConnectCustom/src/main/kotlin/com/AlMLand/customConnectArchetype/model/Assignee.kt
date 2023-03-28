package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_AVATAR_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_EVENTS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_FOLLOWERS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_FOLLOWING_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_GISTS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_GRAVATAR_ID
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_HTML_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_ID
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_LOGIN
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_NODE_ID
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_ORGANIZATIONS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_RECEIVED_EVENTS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_REPOS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_SITE_ADMIN
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_STARRED_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_SUBSCRIPTIONS_URL
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_TYPE
import com.AlMLand.customConnectArchetype.schema.AssigneeField.ASSIGNEE_URL
import org.json.JSONArray
import org.json.JSONObject

class Assignee(
    val login: String?,
    val id: Long?,
    val nodeId: String?,
    val avatarUrl: String?,
    val gravatarId: String?,
    val url: String?,
    val htmlUrl: String?,
    val followersUrl: String?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val organizationsUrl: String?,
    val reposUrl: String?,
    val eventsUrl: String?,
    val receivedEventsUrl: String?,
    val type: String?,
    val siteAdmin: Boolean?,
    val optionalProperties: Map<String, Any>
) {
    companion object Utils {
        fun assigneeFromJson(json: JSONObject?): Assignee? =
            json?.let {
                with(json) {
                    Builder()
                        .login(getString(ASSIGNEE_LOGIN.value))
                        .id(getLong(ASSIGNEE_ID.value))
                        .nodeId(getString(ASSIGNEE_NODE_ID.value))
                        .avatarUrl(getString(ASSIGNEE_AVATAR_URL.value))
                        .gravatarId(getString(ASSIGNEE_GRAVATAR_ID.value))
                        .url(getString(ASSIGNEE_URL.value))
                        .htmlUrl(getString(ASSIGNEE_HTML_URL.value))
                        .followersUrl(getString(ASSIGNEE_FOLLOWERS_URL.value))
                        .followingUrl(getString(ASSIGNEE_FOLLOWING_URL.value))
                        .gistsUrl(getString(ASSIGNEE_GISTS_URL.value))
                        .starredUrl(getString(ASSIGNEE_STARRED_URL.value))
                        .subscriptionsUrl(getString(ASSIGNEE_SUBSCRIPTIONS_URL.value))
                        .organizationsUrl(getString(ASSIGNEE_ORGANIZATIONS_URL.value))
                        .reposUrl(getString(ASSIGNEE_REPOS_URL.value))
                        .eventsUrl(getString(ASSIGNEE_EVENTS_URL.value))
                        .receivedEventsUrl(getString(ASSIGNEE_RECEIVED_EVENTS_URL.value))
                        .type(getString(ASSIGNEE_TYPE.value))
                        .siteAdmin(getBoolean(ASSIGNEE_SITE_ADMIN.value))
                        .build()
                }
            }

        fun assigneeFromJsonArray(json: JSONArray): List<Assignee?> =
            json.mapNotNull { assigneeFromJson(it as JSONObject) }

        data class Builder(
            private var login: String? = null,
            private var id: Long? = null,
            private var nodeId: String? = null,
            private var avatarUrl: String? = null,
            private var gravatarId: String? = null,
            private var url: String? = null,
            private var htmlUrl: String? = null,
            private var followersUrl: String? = null,
            private var followingUrl: String? = null,
            private var gistsUrl: String? = null,
            private var starredUrl: String? = null,
            private var subscriptionsUrl: String? = null,
            private var organizationsUrl: String? = null,
            private var reposUrl: String? = null,
            private var eventsUrl: String? = null,
            private var receivedEventsUrl: String? = null,
            private var type: String? = null,
            private var siteAdmin: Boolean? = null,
            private val optionalProperties: MutableMap<String, Any> = mutableMapOf()
        ) {
            fun login(login: String) = apply { this.login = login }
            fun id(id: Long) = apply { this.id = id }
            fun nodeId(nodeId: String) = apply { this.nodeId = nodeId }
            fun avatarUrl(avatarUrl: String) = apply { this.avatarUrl = avatarUrl }
            fun gravatarId(gravatarId: String) = apply { this.gravatarId = gravatarId }
            fun url(url: String) = apply { this.url = url }
            fun htmlUrl(htmlUrl: String) = apply { this.htmlUrl = htmlUrl }
            fun followersUrl(followersUrl: String) = apply { this.followersUrl = followersUrl }
            fun followingUrl(followingUrl: String) = apply { this.followingUrl = followingUrl }
            fun gistsUrl(gistsUrl: String) = apply { this.gistsUrl = gistsUrl }
            fun starredUrl(starredUrl: String) = apply { this.starredUrl = starredUrl }
            fun subscriptionsUrl(subscriptionsUrl: String) = apply { this.subscriptionsUrl = subscriptionsUrl }
            fun organizationsUrl(organizationsUrl: String) = apply { this.organizationsUrl = organizationsUrl }
            fun reposUrl(reposUrl: String) = apply { this.reposUrl = reposUrl }
            fun eventsUrl(eventsUrl: String) = apply { this.eventsUrl = eventsUrl }
            fun receivedEventsUrl(receivedEventsUrl: String) = apply { this.receivedEventsUrl = receivedEventsUrl }
            fun type(type: String) = apply { this.type = type }
            fun siteAdmin(siteAdmin: Boolean) = apply { this.siteAdmin = siteAdmin }
            fun optionalProperties(key: String, value: Any) = apply { this.optionalProperties[key] = value }
            fun build() = Assignee(
                login, id, nodeId, avatarUrl, gravatarId, url, htmlUrl, followersUrl, followingUrl,
                gistsUrl, starredUrl, subscriptionsUrl, organizationsUrl, reposUrl, eventsUrl, receivedEventsUrl, type,
                siteAdmin, optionalProperties
            )
        }
    }
}