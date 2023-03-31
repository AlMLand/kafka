package com.AlMLand.customConnectArchetype.source

internal enum class GithubSourceConnectorConfigConstants(val value: String) {
    TOPIC_CONFIG("topic"), OWNER_CONFIG("github.owner"), REPO_CONFIG("github.repo"),
    SINCE_CONFIG("since.timestamp"), BATCH_SIZE_CONFIG("batch.size"), AUTH_USERNAME_CONFIG("auth.username"),
    AUTH_PASSWORD_CONFIG("auth.password")
}

internal enum class GithubSourceConnectorDocConstants(val value: String) {
    TOPIC_DOC("Topic to write to"), OWNER_DOC("Owner of the repository you'd like to follow"),
    REPO_DOC("Repository you'd like to follow"),
    SINCE_DOC(
        """
                Only issues updated at or after this time are returned. 
                This is a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ. Defaults to a year from first launch.
            """
    ),
    BATCH_SIZE_DOC("Number of data points to retrieve at a time. Defaults to 100 (max value)"),
    AUTH_USERNAME_DOC("Optional Username to authenticate calls"),
    AUTH_PASSWORD_DOC("Optional Password to authenticate calls")
}