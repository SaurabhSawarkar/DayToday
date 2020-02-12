package com.example.daytodaytest.network

import com.example.daytodaytest.BuildConfig

/**
 * List available environments
 */
interface Environment {
    enum class Type constructor(val value: String) {
        DEBUG("debug"), QA("qa"), RELEASE("release")
    }

    fun baseUrl(): String

    fun type(): Type
}

/**
 * Created debug environment
 */
class DebugEnv : Environment {
    override fun baseUrl(): String = BuildConfig.BASE_URL

    override fun type(): Environment.Type = Environment.Type.DEBUG
}

/**
 * Created debug environment
 */
class QaEnv : Environment {
    override fun baseUrl(): String = BuildConfig.BASE_URL

    override fun type(): Environment.Type = Environment.Type.QA
}

/**
 * Created debug environment
 */
class ReleaseEnv : Environment {
    override fun baseUrl(): String = BuildConfig.BASE_URL

    override fun type(): Environment.Type = Environment.Type.RELEASE
}