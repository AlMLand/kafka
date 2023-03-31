package com.AlMLand.customConnectArchetype.client

import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfig
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.BATCH_SIZE_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.OWNER_CONFIG
import com.AlMLand.customConnectArchetype.source.GithubSourceConnectorConfigConstants.REPO_CONFIG
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.json.JSONArray
import org.slf4j.LoggerFactory
import java.net.ConnectException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.ceil

class GitHubHttpClient(private val connectorConfig: GithubSourceConnectorConfig) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private var xRateLimit = 9999
    private var xRateRemaining = 9999
    private var xRateReset = Instant.MAX.epochSecond

    companion object {
        private const val X_RATE_LIMIT_LIMIT_HEADER = "X-RateLimit-Limit"
        private const val X_RATE_LIMIT_REMAINING_HEADER = "X-RateLimit-Remaining"
        private const val X_RATE_LIMIT_RESET_HEADER = "X-RateLimit-Reset"
        private val SOURCE_URL = """
            https://api.github.com/repos/%s/%s/issues?page=%s&per_page=%s&since=%s&state=all&direction=asc&sort=updated
        """.trimIndent()
    }

    fun nextIssues(page: Int, since: Instant): JSONArray =
        try {
            nextIssuesAPI(page, since).run {
                headers.let { headers ->
                    xRateLimit = headers.getFirst(X_RATE_LIMIT_LIMIT_HEADER).toInt()
                    xRateRemaining = headers.getFirst(X_RATE_LIMIT_REMAINING_HEADER).toInt()
                    xRateReset = headers.getFirst(X_RATE_LIMIT_RESET_HEADER).toLong()
                    when (status) {
                        200 -> body.array

                        401 -> throw ConnectException("bad github credentials provided")

                        403 -> {
                            logger.info(
                                """
                                {}
                                rate limit is {}
                                remaining calls is {}
                                limit will reset at {}
                            """.trimIndent(),
                                body.`object`.getString("message"),
                                xRateLimit,
                                xRateRemaining,
                                LocalDateTime.ofInstant(Instant.ofEpochSecond(xRateReset), ZoneOffset.systemDefault())
                            )
                            delayTime().let {
                                logger.info("sleeping for {}", it)
                                delay(it)
                                nextIssues(page, since)
                            }
                        }

                        else -> {
                            logger.error(
                                """
                                url: {}
                                status: {}
                                body: {}
                                headers: {}
                                unknown error, sleeping 5 seconds before retry
                            """.trimIndent(),
                                constructUrl(page, since),
                                status,
                                body,
                                headers
                            )
                            delay(5000L)
                            nextIssues(page, since)
                        }
                    }
                }
            }
        } catch (e: UnirestException) {
            e.printStackTrace()
            delay(5000L)
            JSONArray()
        }

    private fun delay(delay: Long) {
        Thread.sleep(delay)
    }

    private fun delayTime() = xRateReset - Instant.now().epochSecond

    private fun nextIssuesAPI(page: Int, since: Instant): HttpResponse<JsonNode> =
        Unirest.get(constructUrl(page, since)).let { request ->
            with(connectorConfig) {
                if (authUsername()?.isNotBlank() == true && authPassword()?.isNotBlank() == true)
                    request.basicAuth(authUsername(), authPassword()).asJson()
                else
                    request.asJson()
            }
        }

    private fun constructUrl(page: Int, since: Instant): String =
        String().format(
            SOURCE_URL,
            OWNER_CONFIG.value,
            REPO_CONFIG.value,
            page,
            BATCH_SIZE_CONFIG.value,
            since.toString()
        )

    fun delay() {
        ceil(((xRateReset - Instant.now().epochSecond) / xRateRemaining).toDouble()).let {
            logger.info("sleeping for $it seconds")
            delay(it.toLong())
        }
    }

    fun delayIfNeed() {
        if (xRateRemaining in 1..10) {
            logger.info("Approaching limit soon, you have $xRateRemaining requests left")
            delay()
        }
    }
}
