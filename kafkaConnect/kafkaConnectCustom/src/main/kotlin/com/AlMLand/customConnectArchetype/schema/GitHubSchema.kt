package com.AlMLand.customConnectArchetype.schema

import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_CREATED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_NUMBER
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_PULL_REQUEST
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_STATE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_TITLE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_UPDATED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_USER
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_HTML_URL
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_URL
import com.AlMLand.customConnectArchetype.schema.SchemaName.SCHEMA_ISSUE
import com.AlMLand.customConnectArchetype.schema.SchemaName.SCHEMA_KEY
import com.AlMLand.customConnectArchetype.schema.SchemaName.SCHEMA_PULL_REQUEST
import com.AlMLand.customConnectArchetype.schema.SchemaName.SCHEMA_USER
import com.AlMLand.customConnectArchetype.schema.UserField.USER_ID
import com.AlMLand.customConnectArchetype.schema.UserField.USER_LOGIN
import com.AlMLand.customConnectArchetype.schema.UserField.USER_URL
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.data.Schema.INT32_SCHEMA
import org.apache.kafka.connect.data.Schema.INT64_SCHEMA
import org.apache.kafka.connect.data.Schema.STRING_SCHEMA
import org.apache.kafka.connect.data.SchemaBuilder

enum class SchemaName(val schema: String) {
    SCHEMA_KEY("issue_key"), SCHEMA_ISSUE("issue"), SCHEMA_USER("user"),
    SCHEMA_PULL_REQUEST("pull_request")
}

val USER_SCHEMA: Schema = SchemaBuilder.struct()
    .name(SCHEMA_USER.schema)
    .version(1)
    .field(USER_URL.value, STRING_SCHEMA)
    .field(USER_ID.value, INT32_SCHEMA)
    .field(USER_LOGIN.value, STRING_SCHEMA)
    .build()

val PULL_REQUEST_SCHEMA: Schema = SchemaBuilder.struct()
    .name(SCHEMA_PULL_REQUEST.schema)
    .version(1)
    .field(PULL_REQUEST_URL.value, STRING_SCHEMA)
    .field(PULL_REQUEST_HTML_URL.value, STRING_SCHEMA)
    .optional()
    .build()

val KEY_SCHEMA: Schema = SchemaBuilder.struct()
    .name(SCHEMA_KEY.schema)
    .version(1)
    .field(OWNER_FIELD, STRING_SCHEMA)
    .field(REPOSITORY_FIELD, STRING_SCHEMA)
    .field(ISSUE_NUMBER.value, INT32_SCHEMA)
    .build()

val VALUE_SCHEMA: Schema = SchemaBuilder.struct()
    .name(SCHEMA_ISSUE.schema)
    .version(1)
    .field(ISSUE_URL.value, STRING_SCHEMA)
    .field(ISSUE_TITLE.value, STRING_SCHEMA)
    .field(ISSUE_CREATED_AT.value, INT64_SCHEMA)
    .field(ISSUE_UPDATED_AT.value, INT64_SCHEMA)
    .field(ISSUE_NUMBER.value, INT32_SCHEMA)
    .field(ISSUE_STATE.value, STRING_SCHEMA)
    .field(ISSUE_USER.value, USER_SCHEMA)
    .field(ISSUE_PULL_REQUEST.value, PULL_REQUEST_SCHEMA)
    .build()
