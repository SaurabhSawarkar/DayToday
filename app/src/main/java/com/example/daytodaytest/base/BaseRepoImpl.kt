package com.example.daytodaytest.base

import com.example.daytodaytest.network.AppRxSchedulers
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import io.reactivex.Observable
import io.reactivex.Single

const val DEFAULT_HOST = "www.google.com"
const val NETWORK_NOT_AVAILABLE = "NetworkNotAvailable"
abstract class BaseRepoImpl {

    fun isInternetAvailable(host: String? = null): Single<Boolean> {
        return ReactiveNetwork.checkInternetConnectivity(getInternetObservingSettings(host))
            .subscribeOn(AppRxSchedulers.network())
    }

    private fun getInternetObservingSettings(host: String? = null): InternetObservingSettings {
        return InternetObservingSettings.builder()
            .host(host ?: DEFAULT_HOST)
            .strategy(SocketInternetObservingStrategy())
            .build()
    }

    fun <T> getNetworkNotAvailable(message: String? = null, type: T): Observable<T> {
        return Observable.create<T> { subscriber ->
            subscriber.onError(Throwable(message ?: NETWORK_NOT_AVAILABLE))
            subscriber.onComplete()
        }
    }
}