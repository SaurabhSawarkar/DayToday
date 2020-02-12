package com.example.daytodaytest.network

import android.content.Context
import com.example.daytodaytest.BuildConfig
import com.example.daytodaytest.pref.TokenPref
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


const val RETROFIT = "RETROFIT"
private const val HTTP_CLIENT = "HTTP_CLIENT"
private const val HTTP_LOGGING = "HTTP_LOGGING"
private const val AUTH_INTERCEPTOR = "AUTH_INTERCEPTOR"
private const val AUTH_PICASSO = "AUTH_PICASSO"
const val GSON = "SDK_GSON"

/**
 * Model for retrofit instance creation
 */
val networkModule = module {
    single(named(RETROFIT)) {
        provideRetrofit(get(), get(named(HTTP_CLIENT)), get(named(GSON)))
    }

    factory(named(HTTP_LOGGING)) { providesHttpLogging() }

    factory(named(HTTP_CLIENT)) {
        providesOkHttpClient(get(named(HTTP_LOGGING)), get(named(AUTH_INTERCEPTOR)))
    }

    factory(named(AUTH_INTERCEPTOR)) { AuthenticatorInterceptor(TokenPref(androidContext())) }

    single(named(GSON)) { provideGson() }

    single(named(AUTH_PICASSO)) { getPicasso(androidContext(), get(named(HTTP_CLIENT))) }
}


/**
 * Create the retrofit instance
 */
fun provideRetrofit(
    environment: EnvironmentManager, okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit {
    return Retrofit.Builder().baseUrl(environment.baseUrl()).client(okHttpClient)
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}


fun providesHttpLogging(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
    return interceptor
}

fun provideGson(): Gson {
    return GsonBuilder()
        .setLenient()
        .create()
}

fun providesOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    authenticatorInterceptor: AuthenticatorInterceptor
): OkHttpClient {
    return OkHttpClient.Builder().connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authenticatorInterceptor).build()
}

fun getPicasso(context: Context, okHttpClient: OkHttpClient): Picasso {
    val builder = Picasso.Builder(context)
    builder.loggingEnabled(true)
    return builder.downloader(OkHttp3Downloader(okHttpClient)).build()
}

/**
 * Create an implementation of the API endpoints defined by the service interface.
 */
inline fun <reified T> createWebService(retrofit: Retrofit): T = retrofit.create(T::class.java)
