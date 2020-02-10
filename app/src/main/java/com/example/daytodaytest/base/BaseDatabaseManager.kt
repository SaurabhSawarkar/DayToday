package com.example.daytodaytest.base

import com.example.daytodaytest.extension.findAll
import com.example.daytodaytest.extension.saveAll
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel
import io.realm.kotlin.deleteFromRealm

/**
 * Base class for DatabaseManager's which contains methods for default CRUD operations.
 */
abstract class BaseDatabaseManager {

    lateinit var configuration: RealmConfiguration

    /**
     * Inserts or updates data which is of type list of RealmModel
     */
    fun <T : List<RealmModel>> insertOrUpdate(data: T) = configuration.saveAll(data)

    /**
     * Finds all the classes that are if type [RealmModel] and returns as a list
     */
    fun <T : RealmModel> findAllEntries(clazz: Class<T>): List<T> = configuration.findAll(clazz)

    /**
     * Delete an object from the database by id. Make sure to call this function from inside
     * transaction.
     * @param id the id of the object
     */
    fun <T : RealmModel> Realm.deleteById(clazz: Class<T>, id: String?, idField: String = "id") {
        where(clazz).equalTo(idField, id).findFirst()?.deleteFromRealm()
    }

    /**
     * Clears database records. Implementers should provide the implementation.
     * */
    abstract fun clear(): Observable<Boolean>
}