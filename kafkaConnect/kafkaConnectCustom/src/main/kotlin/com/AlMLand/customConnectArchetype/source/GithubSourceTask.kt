package com.AlMLand.customConnectArchetype.source

import com.AlMLand.customConnectArchetype.client.GitHubHttpClient
import com.AlMLand.customConnectArchetype.exceptions.NotImplementedException
import com.AlMLand.customConnectArchetype.model.Issue
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_CREATED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_NUMBER
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_PULL_REQUEST
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_STATE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_TITLE
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_UPDATED_AT
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_URL
import com.AlMLand.customConnectArchetype.schema.IssueField.ISSUE_USER
import com.AlMLand.customConnectArchetype.schema.KEY_SCHEMA
import com.AlMLand.customConnectArchetype.schema.NEXT_PAGE_FIELD
import com.AlMLand.customConnectArchetype.schema.OWNER
import com.AlMLand.customConnectArchetype.schema.PULL_REQUEST_SCHEMA
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_HTML_URL
import com.AlMLand.customConnectArchetype.schema.PullRequestField.PULL_REQUEST_URL
import com.AlMLand.customConnectArchetype.schema.REPOSITORY
import com.AlMLand.customConnectArchetype.schema.USER_SCHEMA
import com.AlMLand.customConnectArchetype.schema.UserField.USER_ID
import com.AlMLand.customConnectArchetype.schema.UserField.USER_LOGIN
import com.AlMLand.customConnectArchetype.schema.UserField.USER_URL
import com.AlMLand.customConnectArchetype.schema.VALUE_SCHEMA
import org.apache.kafka.connect.data.Struct
import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.source.SourceTask
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.time.Instant

class GithubSourceTask : SourceTask() {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private lateinit var githubSourceConnectorConfig: GithubSourceConnectorConfig
    private lateinit var gitHubHttpClient: GitHubHttpClient
    private lateinit var nextQuerySince: Instant
    private lateinit var lastUpdatedAt: Instant
    private var lastIssueNumber: Int = 0
    private var nextPageToVisit: Int = -1

    override fun stop() = throw NotImplementedException("not implemented feature in ${this.javaClass.simpleName}")

    override fun version(): String = packageVersion(this::class.java)

    override fun start(properpties: MutableMap<String, String>) {
        githubSourceConnectorConfig = GithubSourceConnectorConfig(properpties)
        initVariables()
        gitHubHttpClient = GitHubHttpClient(githubSourceConnectorConfig)
    }

    override fun poll(): List<SourceRecord> =
        mutableListOf<SourceRecord>().apply {
            gitHubHttpClient.delayIfNeed()
            var count = 0
            gitHubHttpClient.nextIssues(nextPageToVisit, nextQuerySince).forEach {
                Issue.issueFromJson(it as JSONObject).let { issue ->
                    add(generateSourceRecord(issue)).also {
                        count.inc()
                        lastUpdatedAt = issue.updatedAt ?: error("updatedAt must not be null")
                    }
                }
            }
            if (count > 0) logger.info("fetched $count record(s)")
            setVariablesAfterFetch(count)
        }

    private fun initVariables() {
        context.offsetStorageReader().offset(sourcePartition())?.let {
            with(it[ISSUE_UPDATED_AT.value]) {
                if (this != null && this is String) nextQuerySince = Instant.parse(this)
            }
            with(it[ISSUE_NUMBER.value]) {
                if (this != null && this is String) lastIssueNumber = this.toInt()
            }
            with(it[NEXT_PAGE_FIELD]) {
                if (this != null && this is String) nextPageToVisit = this.toInt()
            }
        } ?: run {
            nextQuerySince = githubSourceConnectorConfig.sinceConfig()
            lastIssueNumber = -1
        }
    }

    private fun setVariablesAfterFetch(count: Int) {
        if (count == 100) nextPageToVisit += 1
        else {
            nextQuerySince = lastUpdatedAt.plusSeconds(1)
            nextPageToVisit = 1
            gitHubHttpClient.delay()
        }
    }

    private fun generateSourceRecord(issue: Issue): SourceRecord =
        SourceRecord(
            sourcePartition(),
            sourceOffset(issue.updatedAt),
            githubSourceConnectorConfig.topicConfig(),
            null,
            KEY_SCHEMA,
            buildRecordKey(issue),
            VALUE_SCHEMA,
            buildRecordValue(issue),
            issue.updatedAt?.epochSecond ?: error("issue updatedAt must not be null")
        )

    private fun buildRecordValue(issue: Issue): Struct =
        Struct(VALUE_SCHEMA).apply {
            put(ISSUE_URL.value, issue.url)
            put(ISSUE_TITLE.value, issue.title)
            put(ISSUE_CREATED_AT.value, issue.createdAt)
            put(ISSUE_UPDATED_AT.value, issue.updatedAt)
            put(ISSUE_NUMBER.value, issue.number)
            put(ISSUE_STATE.value, issue.state)
            with(issue.user) {
                if (this != null) {
                    put(ISSUE_USER.value, Struct(USER_SCHEMA)
                        .apply {
                            put(USER_URL.value, url)
                            put(USER_ID.value, id)
                            put(USER_LOGIN.value, login)
                        }
                    )
                }
            }
            with(issue.pullRequest) {
                if (this != null) {
                    put(ISSUE_PULL_REQUEST.value, Struct(PULL_REQUEST_SCHEMA)
                        .apply {
                            put(PULL_REQUEST_URL.value, url)
                            put(PULL_REQUEST_HTML_URL.value, htmlUrl)
                        }
                    )
                }
            }
        }

    private fun buildRecordKey(issue: Issue): Struct =
        Struct(KEY_SCHEMA).apply {
            put(OWNER, githubSourceConnectorConfig.ownerConfig())
            put(REPOSITORY, githubSourceConnectorConfig.repoConfig())
            put(ISSUE_NUMBER.value, issue.number)
        }

    private fun sourcePartition(): Map<String, String> =
        mapOf(
            OWNER to githubSourceConnectorConfig.ownerConfig(),
            REPOSITORY to githubSourceConnectorConfig.repoConfig()
        )

    private fun sourceOffset(updatedAt: Instant?): Map<String, String> =
        mapOf(
            ISSUE_UPDATED_AT.value to maxInstant(updatedAt),
            NEXT_PAGE_FIELD to githubSourceConnectorConfig.repoConfig()
        )

    private fun maxInstant(updatedAt: Instant?): String =
        (if (updatedAt != null && updatedAt > nextQuerySince) updatedAt else nextQuerySince).toString()
}
