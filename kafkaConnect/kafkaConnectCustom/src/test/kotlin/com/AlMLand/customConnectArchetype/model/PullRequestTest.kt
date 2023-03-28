package com.AlMLand.customConnectArchetype.model

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PullRequestTest {
    @Test
    fun `PullRequest from json is equal to expected object`() {
        val expected = PullRequest.Utils.Builder()
            .url("https://api.github.com/repos/kubernetes/kubernetes/pulls/116869")
            .htmlUrl("https://github.com/kubernetes/kubernetes/pull/116869")
            .diffUrl("https://github.com/kubernetes/kubernetes/pull/116869.diff")
            .patchUrl("https://github.com/kubernetes/kubernetes/pull/116869.patch")
            .build()

        val actual = PullRequest.pullRequestFromJson(JSONObject(gitHubPullRequestJson))

        assertEquals(expected.url, actual.url)
        assertEquals(expected.htmlUrl, actual.htmlUrl)
        assertEquals(expected.diffUrl, actual.diffUrl)
        assertEquals(expected.patchUrl, actual.patchUrl)
        assertEquals(expected.mergedAt, actual.mergedAt)
    }
}
