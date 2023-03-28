package com.AlMLand.customConnectArchetype.model

import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_ACTIVE_LOCK_REASON
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_ASSIGNEE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_ASSIGNEES
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_AUTHOR_ASSOCIATION
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_BODY
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_CLOSED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_COMMENTS
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_COMMENTS_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_CREATED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_DRAFT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_EVENTS_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_HTML_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_ID
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_LABELS
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_LABELS_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_LOCKED
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_MILESTONE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_NODE_ID
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_NUMBER
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_PERFORMED_VIA_GITHUB_APP
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_PULL_REQUEST
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_REACTIONS
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_REPOSITORY_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_STATE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_STATE_REASON
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_TIMELINE_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_TITLE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_UPDATED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_USER
import org.json.JSONException
import org.json.JSONObject

class Issue(
    val url: String?,
    val repositoryUrl: String?,
    val labelsUrl: String?,
    val commentsUrl: String?,
    val eventsUrl: String?,
    val htmlUrl: String?,
    val id: Long?,
    val nodeId: String?,
    val number: Int?,
    val title: String?,
    val user: User?,
    val labels: List<Label?>?,
    val state: String?,
    val locked: Boolean?,
    val assignee: Assignee?,
    val assignees: List<Assignee?>?,
    val milestone: String?,
    val comments: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val closedAt: String?,
    val authorAssociation: String?,
    val activeLockReason: String?,
    val draft: Boolean?,
    val pullRequest: PullRequest?,
    val body: String?,
    val reactions: Reactions?,
    val timelineUrl: String?,
    val performedViaGithubApp: String?,
    val stateReason: String?,
    val optionalProperties: Map<String, Any>
) {
    companion object Utils {
        fun issueFromJson(json: JSONObject): Issue =
            with(json) {
                Builder()
                    .url(getString(ISSUE_URL.value))
                    .repositoryUrl(getString(ISSUE_REPOSITORY_URL.value))
                    .labelsUrl(getString(ISSUE_LABELS_URL.value))
                    .commentsUrl(getString(ISSUE_COMMENTS_URL.value))
                    .eventsUrl(getString(ISSUE_EVENTS_URL.value))
                    .htmlUrl(getString(ISSUE_HTML_URL.value))
                    .id(getLong(ISSUE_ID.value))
                    .nodeId(getString(ISSUE_NODE_ID.value))
                    .number(getInt(ISSUE_NUMBER.value))
                    .title(getString(ISSUE_TITLE.value))
                    .user(User.userFromJson(getJSONObject(ISSUE_USER.value)))
                    .labels(Label.labelFromJsonArray(getJSONArray(ISSUE_LABELS.value)))
                    .state(getString(ISSUE_STATE.value))
                    .locked(getBoolean(ISSUE_LOCKED.value))
                    .assignee(Assignee.assigneeFromJson(getJSONObjectOrNull(ISSUE_ASSIGNEE.value)))
                    .assignees(Assignee.assigneeFromJsonArray(getJSONArray(ISSUE_ASSIGNEES.value)))
                    .milestone(getStringOrNull(ISSUE_MILESTONE.value))
                    .comments(getInt(ISSUE_COMMENTS.value))
                    .createdAt(getString(ISSUE_CREATED_AT.value))
                    .updatedAt(getString(ISSUE_UPDATED_AT.value))
                    .closedAt(getStringOrNull(ISSUE_CLOSED_AT.value))
                    .authorAssociation(getString(ISSUE_AUTHOR_ASSOCIATION.value))
                    .activeLockReason(getStringOrNull(ISSUE_ACTIVE_LOCK_REASON.value))
                    .draft(getBoolean(ISSUE_DRAFT.value))
                    .pullRequest(PullRequest.pullRequestFromJson(getJSONObject(ISSUE_PULL_REQUEST.value)))
                    .body(getString(ISSUE_BODY.value))
                    .reactions(Reactions.reactionsFromJson(getJSONObject(ISSUE_REACTIONS.value)))
                    .timelineUrl(getString(ISSUE_TIMELINE_URL.value))
                    .performedViaGithubApp(getStringOrNull(ISSUE_PERFORMED_VIA_GITHUB_APP.value))
                    .stateReason(getStringOrNull(ISSUE_STATE_REASON.value))
                    .build()
            }

        private fun JSONObject.getJSONObjectOrNull(field: String): JSONObject? =
            try {
                getJSONObject(field)
            } catch (e: JSONException) {
                null
            }

        private fun JSONObject.getStringOrNull(field: String): String? =
            try {
                getString(field)
            } catch (e: JSONException) {
                null
            }

        data class Builder(
            private var url: String? = null,
            private var repositoryUrl: String? = null,
            private var labelsUrl: String? = null,
            private var commentsUrl: String? = null,
            private var eventsUrl: String? = null,
            private var htmlUrl: String? = null,
            private var id: Long? = null,
            private var nodeId: String? = null,
            private var number: Int? = null,
            private var title: String? = null,
            private var user: User? = null,
            private var labels: List<Label?>? = null,
            private var state: String? = null,
            private var locked: Boolean? = null,
            private var assignee: Assignee? = null,
            private var assignees: List<Assignee?>? = null,
            private var milestone: String? = null,
            private var comments: Int? = null,
            private var createdAt: String? = null,
            private var updatedAt: String? = null,
            private var closedAt: String? = null,
            private var authorAssociation: String? = null,
            private var activeLockReason: String? = null,
            private var draft: Boolean? = null,
            private var pullRequest: PullRequest? = null,
            private var body: String? = null,
            private var reactions: Reactions? = null,
            private var timelineUrl: String? = null,
            private var performedViaGithubApp: String? = null,
            private var stateReason: String? = null,
            private val optionalProperties: MutableMap<String, Any> = mutableMapOf()
        ) {
            fun url(url: String) = apply { this.url = url }
            fun repositoryUrl(repositoryUrl: String) = apply { this.repositoryUrl = repositoryUrl }
            fun labelsUrl(labelsUrl: String) = apply { this.labelsUrl = labelsUrl }
            fun commentsUrl(commentsUrl: String) = apply { this.commentsUrl = commentsUrl }
            fun eventsUrl(eventsUrl: String) = apply { this.eventsUrl = eventsUrl }
            fun htmlUrl(htmlUrl: String) = apply { this.htmlUrl = htmlUrl }
            fun id(id: Long) = apply { this.id = id }
            fun nodeId(nodeId: String) = apply { this.nodeId = nodeId }
            fun number(number: Int) = apply { this.number = number }
            fun title(title: String) = apply { this.title = title }
            fun user(user: User) = apply { this.user = user }
            fun labels(labels: List<Label?>) = apply { this.labels = labels }
            fun state(state: String?) = apply { this.state = state }
            fun locked(locked: Boolean) = apply { this.locked = locked }
            fun assignee(assignee: Assignee?) = apply { this.assignee = assignee }
            fun assignees(assignees: List<Assignee?>) = apply { this.assignees = assignees }
            fun milestone(milestone: String?) = apply { this.milestone = milestone }
            fun comments(comments: Int) = apply { this.comments = comments }
            fun createdAt(createdAt: String) = apply { this.createdAt = createdAt }
            fun updatedAt(updatedAt: String?) = apply { this.updatedAt = updatedAt }
            fun closedAt(closedAt: String?) = apply { this.closedAt = closedAt }
            fun authorAssociation(authorAssociation: String) = apply { this.authorAssociation = authorAssociation }
            fun activeLockReason(activeLockReason: String?) = apply { this.activeLockReason = activeLockReason }
            fun draft(draft: Boolean) = apply { this.draft = draft }
            fun pullRequest(pullRequest: PullRequest) = apply { this.pullRequest = pullRequest }
            fun body(body: String) = apply { this.body = body }
            fun reactions(reactions: Reactions) = apply { this.reactions = reactions }
            fun timelineUrl(timelineUrl: String) = apply { this.timelineUrl = timelineUrl }
            fun performedViaGithubApp(performedViaGithubApp: String?) =
                apply { this.performedViaGithubApp = performedViaGithubApp }

            fun stateReason(stateReason: String?) = apply { this.stateReason = stateReason }
            fun optionalProperties(key: String, value: Any) = apply { this.optionalProperties[key] = value }
            fun build() = Issue(
                url,
                repositoryUrl,
                labelsUrl,
                commentsUrl,
                eventsUrl,
                htmlUrl,
                id,
                nodeId,
                number,
                title,
                user,
                labels,
                state,
                locked,
                assignee,
                assignees,
                milestone,
                comments,
                createdAt,
                updatedAt,
                closedAt,
                authorAssociation,
                activeLockReason,
                draft,
                pullRequest,
                body,
                reactions,
                timelineUrl,
                performedViaGithubApp,
                stateReason,
                optionalProperties
            )
        }
    }
}