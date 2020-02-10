package com.example.daytodaytest.network

/**
 * Environment indicates the base url for the API calls.
 */
class EnvironmentManager(env: Environment.Type) : Environment {

    private val environment: Environment = when (env) {
        Environment.Type.DEBUG -> DebugEnv()
        Environment.Type.QA -> QaEnv()
        Environment.Type.RELEASE -> ReleaseEnv()
    }

    override fun baseUrl(): String = environment.baseUrl()

    override fun type(): Environment.Type = environment.type()
}