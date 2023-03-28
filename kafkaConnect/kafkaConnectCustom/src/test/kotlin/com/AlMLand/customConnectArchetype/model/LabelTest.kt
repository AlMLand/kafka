package com.AlMLand.customConnectArchetype.model

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LabelTest {
    @Test
    fun `Label from json is equal to expected object`() {
        val expected = Label.Utils.Builder()
            .id(253450934)
            .nodeId("MDU6TGFiZWwyNTM0NTA5MzQ=")
            .url("https://api.github.com/repos/kubernetes/kubernetes/labels/size/M")
            .name("size/M")
            .color("eebb00")
            .default(false)
            .description("Denotes a PR that changes 30-99 lines, ignoring generated files.")
            .build()

        val actual = Label.labelFromJson(JSONObject(gitHubLabelJson))

        assertEquals(expected.id, actual.id)
        assertEquals(expected.nodeId, actual.nodeId)
        assertEquals(expected.url, actual.url)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.color, actual.color)
        assertEquals(expected.default, actual.default)
        assertEquals(expected.description, actual.description)
    }
}