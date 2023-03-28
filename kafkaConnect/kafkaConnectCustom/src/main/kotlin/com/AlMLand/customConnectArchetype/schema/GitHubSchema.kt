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

const val OWNER_FIELD = "owner"
const val REPOSITORY_FIELD = "repository"
const val NEXT_PAGE_FIELD = "next_page"

enum class IssueField(val value: String) {
    ISSUE_URL("url"), ISSUE_REPOSITORY_URL("repository_url"), ISSUE_LABELS_URL("labels_url"),
    ISSUE_COMMENTS_URL("comments_url"), ISSUE_EVENTS_URL("events_url"),
    ISSUE_HTML_URL("html_url"), ISSUE_ID("id"), ISSUE_NODE_ID("node_id"),
    ISSUE_NUMBER("number"), ISSUE_TITLE("title"), ISSUE_USER("user"), ISSUE_LABELS("labels"),
    ISSUE_STATE("state"), ISSUE_LOCKED("locked"), ISSUE_ASSIGNEE("assignee"),
    ISSUE_ASSIGNEES("assignees"), ISSUE_MILESTONE("milestone"), ISSUE_COMMENTS("comments"),
    ISSUE_CREATED_AT("created_at"), ISSUE_UPDATED_AT("updated_at"), ISSUE_CLOSED_AT("closed_at"),
    ISSUE_AUTHOR_ASSOCIATION("author_association"), ISSUE_ACTIVE_LOCK_REASON("active_lock_reason"),
    ISSUE_DRAFT("draft"), ISSUE_PULL_REQUEST("pull_request"), ISSUE_BODY("body"),
    ISSUE_REACTIONS("reactions"), ISSUE_TIMELINE_URL("timeline_url"),
    ISSUE_PERFORMED_VIA_GITHUB_APP("performed_via_github_app"), ISSUE_STATE_REASON("state_reason")
}

enum class UserField(val value: String) {
    USER_LOGIN("login"), USER_ID("id"), USER_NODE_ID("node_id"),
    USER_AVATAR_URL("avatar_url"), USER_GRAVATAR_URL("gravatar_id"), USER_URL("url"),
    USER_HTML_URL("html_url"), USER_FOLLOWERS_URL("followers_url"),
    USER_FOLLOWING_URL("following_url"), USER_GISTS_URL("gists_url"), USER_STARRED_URL("starred_url"),
    USER_SUBSCRIPTIONS_URL("subscriptions_url"), USER_ORGANIZATIONS_URL("organizations_url"),
    USER_REPOS_URL("repos_url"), USER_EVENTS_URL("events_url"),
    USER_RECEIVED_EVENTS_URL("received_events_url"), USER_TYPE("type"), USER_SITE_ADMIN("site_admin")
}

enum class LabelField(val value: String) {
    LABEL_ID("id"), LABEL_NODE_ID("node_id"), LABEL_URL("url"), LABEL_NAME("name"),
    LABEL_COLOR("color"), LABEL_DEFAULT("default"), LABEL_DESCRIPTION("description")
}

enum class AssigneeField(val value: String) {
    ASSIGNEE_LOGIN("login"), ASSIGNEE_ID("id"), ASSIGNEE_NODE_ID("node_id"),
    ASSIGNEE_AVATAR_URL("avatar_url"), ASSIGNEE_GRAVATAR_ID("gravatar_id"), ASSIGNEE_URL("url"),
    ASSIGNEE_HTML_URL("html_url"), ASSIGNEE_FOLLOWERS_URL("followers_url"),
    ASSIGNEE_FOLLOWING_URL("following_url"), ASSIGNEE_GISTS_URL("gists_url"),
    ASSIGNEE_STARRED_URL("starred_url"), ASSIGNEE_SUBSCRIPTIONS_URL("subscriptions_url"),
    ASSIGNEE_ORGANIZATIONS_URL("organizations_url"), ASSIGNEE_REPOS_URL("repos_url"),
    ASSIGNEE_EVENTS_URL("events_url"), ASSIGNEE_RECEIVED_EVENTS_URL("received_events_url"),
    ASSIGNEE_TYPE("type"), ASSIGNEE_SITE_ADMIN("site_admin")
}

enum class PullRequestField(val value: String) {
    PULL_REQUEST_URL("url"), PULL_REQUEST_HTML_URL("html_url"),
    PULL_REQUEST_DIFF_URL("diff_url"), PULL_REQUEST_PATCH_URL("patch_url"),
    PULL_REQUEST_MERGED_AT("merged_at")
}

enum class ReactionsField(val value: String) {
    REACTIONS_URL("url"), REACTIONS_TOTAL_COUNT("total_count"), REACTIONS_PLUS_ONE("+1"),
    REACTIONS_MINUS_ONE("-1"), REACTIONS_LAUGH("laugh"), REACTIONS_HOORAY("hooray"),
    REACTIONS_CONFUSED("confused"), REACTIONS_HEART("heart"), REACTIONS_ROCKET("rocket"),
    REACTIONS_EYES("eyes")
}

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
