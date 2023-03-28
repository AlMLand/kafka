package com.AlMLand.customConnectArchetype.model

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AssigneeTest {

    @Test
    fun `Assignee from json is equal to expected object`() {
        val expected = Assignee.Utils.Builder()
            .login("mborsz")
            .id(2559168)
            .nodeId("MDQ6VXNlcjI1NTkxNjg=")
            .avatarUrl("https://avatars.githubusercontent.com/u/2559168?v=4")
            .gravatarId("")
            .url("https://api.github.com/users/mborsz")
            .htmlUrl("https://github.com/mborsz")
            .followersUrl("https://api.github.com/users/mborsz/followers")
            .followingUrl("https://api.github.com/users/mborsz/following{/other_user}")
            .gistsUrl("https://api.github.com/users/mborsz/gists{/gist_id}")
            .starredUrl("https://api.github.com/users/mborsz/starred{/owner}{/repo}")
            .subscriptionsUrl("https://api.github.com/users/mborsz/subscriptions")
            .organizationsUrl("https://api.github.com/users/mborsz/orgs")
            .reposUrl("https://api.github.com/users/mborsz/repos")
            .eventsUrl("https://api.github.com/users/mborsz/events{/privacy}")
            .receivedEventsUrl("https://api.github.com/users/mborsz/received_events")
            .type("User")
            .siteAdmin(false)
            .build()

        val actual = Assignee.assigneeFromJson(JSONObject(gitHubAssigneeJson))

        assertEquals(expected.login, actual?.login)
        assertEquals(expected.id, actual?.id)
        assertEquals(expected.nodeId, actual?.nodeId)
        assertEquals(expected.avatarUrl, actual?.avatarUrl)
        assertEquals(expected.gravatarId, actual?.gravatarId)
        assertEquals(expected.url, actual?.url)
        assertEquals(expected.htmlUrl, actual?.htmlUrl)
        assertEquals(expected.followersUrl, actual?.followersUrl)
        assertEquals(expected.followingUrl, actual?.followingUrl)
        assertEquals(expected.gistsUrl, actual?.gistsUrl)
        assertEquals(expected.starredUrl, actual?.starredUrl)
        assertEquals(expected.subscriptionsUrl, actual?.subscriptionsUrl)
        assertEquals(expected.organizationsUrl, actual?.organizationsUrl)
        assertEquals(expected.reposUrl, actual?.reposUrl)
        assertEquals(expected.eventsUrl, actual?.eventsUrl)
        assertEquals(expected.receivedEventsUrl, actual?.receivedEventsUrl)
        assertEquals(expected.type, actual?.type)
        assertEquals(expected.siteAdmin, actual?.siteAdmin)
    }
}