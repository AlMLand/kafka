package com.AlMLand.customConnectArchetype.exceptions

internal class NotImplementedException(message: String, throwable: Throwable? = null) :
    RuntimeException(message, throwable)

internal class ConfigDefValidatorException(message: String, throwable: Throwable? = null) :
    RuntimeException(message, throwable)