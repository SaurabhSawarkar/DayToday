package com.example.daytodaytest

import android.app.Application
import com.example.daytodaytest.dashboard.database.dashboardDatabaseModule
import com.example.daytodaytest.network.networkModule
import com.example.daytodaytest.pref.TokenPref
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for the app
 */
class DayTodayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val pres = TokenPref(this@DayTodayApplication)
        pres.setAccessToken("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MWUwOWVlODkyNzdmYmQ2MzM2NWJjOGIyYjNmMTU2ZSIsInN1YiI6IjVlNDE5NGYxNDE0NjVjMDAxYWNlNjE3NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1R-OxPZXEGXm1B74qZq6NEpDXuat2PoD764kjLj6wYQ")
        pres.setAPIKey("51e09ee89277fbd63365bc8b2b3f156e")

        startKoin {
            androidContext(this@DayTodayApplication)
            modules(listOf(networkModule, dayTodayModule, dashboardDatabaseModule))
        }

        Realm.init(this)
    }
}