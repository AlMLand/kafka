package com.AlMLand.customConnectArchetype.source

private const val FALLBACK_VERSION = "0.0.0.0"

internal fun packageVersion(clazz: Class<*>): String =
    try {
        clazz.`package`.implementationVersion.let {
            if (it.isNullOrBlank()) FALLBACK_VERSION
            else it
        }
    } catch (t: Throwable) {
        FALLBACK_VERSION
    }
