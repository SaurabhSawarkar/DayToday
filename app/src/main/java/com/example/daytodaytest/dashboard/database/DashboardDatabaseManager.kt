package com.example.daytodaytest.dashboard.database

import com.example.daytodaytest.BuildConfig
import com.example.daytodaytest.base.BaseDatabaseManager
import com.example.daytodaytest.dashboard.model.MoviesResponse
import com.example.daytodaytest.dashboard.model.Results
import com.example.daytodaytest.extension.getRealmInstance
import com.example.daytodaytest.extension.transaction
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Database manager to perform CRUD for dashboard data
 *
 * @param encryptionKey - encryption key for DB
 * @param databaseMigration - handles the DB migration
 */
class DashboardDatabaseManager(
    encryptionKey: ByteArray,
    databaseMigration: DashboardDatabaseMigration
) : BaseDatabaseManager() {

    private val SCHEMA_VERSION = 1L
    private val DATABASE_NAME = "day_today.realm"

    init {
        val builder = RealmConfiguration.Builder().schemaVersion(SCHEMA_VERSION).name(DATABASE_NAME)
            .modules(DashboardRealmModule()).migration(databaseMigration)
        if (!BuildConfig.DEBUG) {
            builder.encryptionKey(encryptionKey)
        }
        configuration = builder.build()
    }


    /**
     * Insert or Update list to the database
     *
     * @param Results - List if Results to be inserted
     */
    fun insertOrUpdateResults(resultsList: List<Results>) {
        getRealmInstance(configuration).transaction { realm ->
            resultsList.forEach { plan ->
                deleteResultInternally(plan, realm)
            }
        }
        insertOrUpdate(resultsList)
    }

    /**
     * Deletes the Results
     *
     * @param result : result to be deleted
     * @param realm: Realm Instance to be used
     */
    private fun deleteResultInternally(result: Results, realm: Realm) {
        // Delete Parent object
        result.id?.let { id ->
            realm.deleteById(Results::class.java, id)
        }
    }


    /**
     * Fetch all Medication Plans
     */
    fun getAllMovies(): List<Results> {
        return findAllEntries(Results::class.java)
    }


    override fun clear(): Observable<Boolean> {
        return Observable.create { emitter ->
            getRealmInstance(configuration).transaction { realm ->
                realm.deleteAll()
            }
            emitter.onNext(true)
            emitter.onComplete()
        }
    }
}