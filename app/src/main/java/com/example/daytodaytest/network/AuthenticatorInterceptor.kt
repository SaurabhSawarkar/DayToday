package com.example.daytodaytest.network

import com.example.daytodaytest.pref.TokenPref
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Takes care of adding authorization header to each request
 */
class AuthenticatorInterceptor(val tokenPref: TokenPref) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        //Add token
        val accessToken = tokenPref.getAccessToken()
        accessToken?.let {
            builder.addHeader("Authorization", "Bearer $it").build()
        }
        builder.method(original.method(), original.body())
        val modifiedRequest = builder.build()
        return chain.proceed(modifiedRequest)
    }
}