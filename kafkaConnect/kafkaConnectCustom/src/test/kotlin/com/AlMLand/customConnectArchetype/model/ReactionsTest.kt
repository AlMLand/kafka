package com.AlMLand.customConnectArchetype.model

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReactionsTest {

    @Test
    fun `Reactions from json is equal to expected object`() {
        val expected = Reactions.Utils.Builder()
            .url("https://api.github.com/repos/kubernetes/kubernetes/issues/116952/reactions")
            .totalCount(0)
            .plusOne(0)
            .minusOne(0)
            .laugh(0)
            .hooray(0)
            .confused(0)
            .heart(0)
            .rocket(0)
            .eyes(0)
            .build()

        val actual = Reactions.reactionsFromJson(JSONObject(gitHubReactionsJson))

        assertEquals(expected.url, actual.url)
        assertEquals(expected.totalCount, actual.totalCount)
        assertEquals(expected.plusOne, actual.plusOne)
        assertEquals(expected.minusOne, actual.minusOne)
        assertEquals(expected.laugh, actual.laugh)
        assertEquals(expected.hooray, actual.hooray)
        assertEquals(expected.confused, actual.confused)
        assertEquals(expected.heart, actual.heart)
        assertEquals(expected.rocket, actual.rocket)
        assertEquals(expected.eyes, actual.eyes)
    }
}