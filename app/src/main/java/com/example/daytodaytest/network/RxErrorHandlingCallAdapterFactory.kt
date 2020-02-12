package com.example.daytodaytest.network

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

/**
 * Retrofit error handling adapter. Only errors are handled here.
 * Error handling logic can be handled here
 */
class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(
        Schedulers.io()
    )

    companion object {

        /**
         * Creates the instance of [RxErrorHandlingCallAdapterFactory]
         */
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<*, *>
        return RxCallAdapterWrapper(retrofit, wrapped)
    }

    private class RxCallAdapterWrapper<R>(
        private val retrofit: Retrofit, private val wrapped: CallAdapter<R, *>
    ) :
        CallAdapter<R, Observable<R>> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Observable<R> {
            return (wrapped.adapt(call) as Observable<R>)
                .onErrorResumeNext { throwable: Throwable ->
                    Observable.error(throwable)
                }
        }
    }
}