package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.UserField.USER_AVATAR_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_EVENTS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_FOLLOWERS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_FOLLOWING_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_GISTS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_GRAVATAR_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_HTML_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_ID
import com.AlMLand.customConnectArchetype.schema.UserField.USER_LOGIN
import com.AlMLand.customConnectArchetype.schema.UserField.USER_NODE_ID
import com.AlMLand.customConnectArchetype.schema.UserField.USER_ORGANIZATIONS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_RECEIVED_EVENTS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_REPOS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_SITE_ADMIN
import com.AlMLand.customConnectArchetype.schema.UserField.USER_STARRED_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_SUBSCRIPTIONS_URL
import com.AlMLand.customConnectArchetype.schema.UserField.USER_TYPE
import com.AlMLand.customConnectArchetype.schema.UserField.USER_URL
import org.json.JSONObject

class User private constructor(
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
        fun userFromJson(json: JSONObject): User =
            with(json) {
                Builder()
                    .login(getString(USER_LOGIN.value))
                    .id(getLong(USER_ID.value))
                    .nodeId(getString(USER_NODE_ID.value))
                    .avatarUrl(getString(USER_AVATAR_URL.value))
                    .gravatarId(getString(USER_GRAVATAR_URL.value))
                    .url(getString(USER_URL.value))
                    .htmlUrl(getString(USER_HTML_URL.value))
                    .followersUrl(getString(USER_FOLLOWERS_URL.value))
                    .followingUrl(getString(USER_FOLLOWING_URL.value))
                    .gistsUrl(getString(USER_GISTS_URL.value))
                    .starredUrl(getString(USER_STARRED_URL.value))
                    .subscriptionsUrl(getString(USER_SUBSCRIPTIONS_URL.value))
                    .organizationsUrl(getString(USER_ORGANIZATIONS_URL.value))
                    .reposUrl(getString(USER_REPOS_URL.value))
                    .eventsUrl(getString(USER_EVENTS_URL.value))
                    .receivedEventsUrl(getString(USER_RECEIVED_EVENTS_URL.value))
                    .type(getString(USER_TYPE.value))
                    .siteAdmin(getBoolean(USER_SITE_ADMIN.value))
                    .build()
            }

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
            fun build() = User(
                login, id, nodeId, avatarUrl, gravatarId, url, htmlUrl, followersUrl, followingUrl,
                gistsUrl, starredUrl, subscriptionsUrl, organizationsUrl, reposUrl, eventsUrl, receivedEventsUrl, type,
                siteAdmin, optionalProperties
            )
        }
    }
}