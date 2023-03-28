package com.AlMLand.customConnectArchetype.model

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun `User from json is equal to expected object`() {
        val expected = User.Utils.Builder()
            .login("pohly")
            .id(22076)
            .nodeId("MDQ6VXNlcjIyMDc2")
            .avatarUrl("https://avatars.githubusercontent.com/u/22076?v=4")
            .gravatarId("")
            .url("https://api.github.com/users/pohly")
            .htmlUrl("https://github.com/pohly")
            .followersUrl("https://api.github.com/users/pohly/followers")
            .followingUrl("https://api.github.com/users/pohly/following{/other_user}")
            .gistsUrl("https://api.github.com/users/pohly/gists{/gist_id}")
            .starredUrl("https://api.github.com/users/pohly/starred{/owner}{/repo}")
            .subscriptionsUrl("https://api.github.com/users/pohly/subscriptions")
            .organizationsUrl("https://api.github.com/users/pohly/orgs")
            .reposUrl("https://api.github.com/users/pohly/repos")
            .eventsUrl("https://api.github.com/users/pohly/events{/privacy}")
            .receivedEventsUrl("https://api.github.com/users/pohly/received_events")
            .type("User")
            .siteAdmin(false)
            .build()

        val actual = User.userFromJson(JSONObject(gitHubUserJson))

        assertEquals(expected.login, actual.login)
        assertEquals(expected.id, actual.id)
        assertEquals(expected.nodeId, actual.nodeId)
        assertEquals(expected.avatarUrl, actual.avatarUrl)
        assertEquals(expected.gravatarId, actual.gravatarId)
        assertEquals(expected.url, actual.url)
        assertEquals(expected.htmlUrl, actual.htmlUrl)
        assertEquals(expected.followersUrl, actual.followersUrl)
        assertEquals(expected.followingUrl, actual.followingUrl)
        assertEquals(expected.gistsUrl, actual.gistsUrl)
        assertEquals(expected.starredUrl, actual.starredUrl)
        assertEquals(expected.subscriptionsUrl, actual.subscriptionsUrl)
        assertEquals(expected.organizationsUrl, actual.organizationsUrl)
        assertEquals(expected.reposUrl, actual.reposUrl)
        assertEquals(expected.eventsUrl, actual.eventsUrl)
        assertEquals(expected.receivedEventsUrl, actual.receivedEventsUrl)
        assertEquals(expected.type, actual.type)
        assertEquals(expected.siteAdmin, actual.siteAdmin)
    }
}