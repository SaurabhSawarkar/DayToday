package com.example.daytodaytest.pref

import android.content.Context

private const val PREF_NAME = "TOKEN_PREF"
private const val KEY_ACCESS_TOKEN = "key_access_token"
private const val KEY_API = "key_api"

/**
 * Takes care of app token
 */
class TokenPref(context: Context) : AbstractSharedPref(context, PREF_NAME) {

    public fun setAccessToken(token: String) {
        put(KEY_ACCESS_TOKEN, token)
    }

    fun getAccessToken(): String? {
        return getString(KEY_ACCESS_TOKEN)
    }

    public fun setAPIKey(token: String) {
        put(KEY_API, token)
    }

    fun getAPIKey(): String? {
        return getString(KEY_API)
    }
}