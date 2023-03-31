package com.AlMLand.customConnectArchetype.schema

const val OWNER = "owner"
const val REPOSITORY = "repository"
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
