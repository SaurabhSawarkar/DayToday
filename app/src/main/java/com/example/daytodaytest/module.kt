package com.example.daytodaytest

import com.example.daytodaytest.dashboard.*
import com.example.daytodaytest.network.Environment
import com.example.daytodaytest.network.EnvironmentManager
import com.example.daytodaytest.network.RETROFIT
import com.example.daytodaytest.network.createWebService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module
 */
val dayTodayModule = module(override = true) {
    single<DashboardRepo> {
        DashboardRepoImpl(
            createWebService<DashboardAPI>(get(named(RETROFIT))),
            get()
        )
    }
    factory { EnvironmentManager(Environment.Type.DEBUG) }
    viewModel { DashboardViewModel(get()) }
}