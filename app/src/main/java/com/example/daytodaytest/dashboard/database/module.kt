package com.example.daytodaytest.dashboard.database

import org.koin.dsl.module

/**
 * Koin dependency injection for database
 */
val dashboardDatabaseModule = module {
    single { DashboardDatabaseManager(ByteArray(64), DashboardDatabaseMigration()) }
}