package com.example.daytodaytest.extension

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel


/**
 * Returns the realm instance
 * @param config the configuration of the realm instance
 */
fun getRealmInstance(config: RealmConfiguration): Realm {
    return Realm.getInstance(config)
}


/**
 * Utility extension for modifying database. Create a transaction, run the function passed as argument,
 * commit transaction and close realm instance.
 */
fun Realm.transaction(action: (Realm) -> Unit) {
    use { executeTransaction { action(this) } }
}

/**
 * checks if the realm instance has a primary key
 * @param realm the realm instance to be checked
 */
fun <T : RealmModel> T.hasPrimaryKey(realm: Realm): Boolean {
    if (realm.schema.get(this.javaClass.simpleName) == null) {
        throw IllegalArgumentException(this.javaClass.simpleName + " is not part of the schema for this Realm. Did you added realm-android plugin in your build.gradle file?")
    }
    return realm.schema.get(this.javaClass.simpleName)?.hasPrimaryKey() == true
}

/**
 * Creates a new entries in database or updates an existing one. If entity has no primary key, always create a new one.
 * If has primary key, it tries to updates an existing one.
 * @param data the data that is to be saved in db
 */
inline fun <reified T : RealmModel> RealmConfiguration.saveAll(data: Collection<T>): List<T> {
    val results = mutableListOf<T>()
    getRealmInstance(this).transaction { realm ->
        data.forEach {
            results += if (it.hasPrimaryKey(realm)) realm.copyToRealmOrUpdate(it) else realm.copyToRealm(
                it
            )
        }
    }
    return results
}

/**
 * Query to the database with RealmQuery instance as argument and returns all items founded
 */
fun <T : RealmModel> RealmConfiguration.findAll(clazz: Class<T>): List<T> {
    getRealmInstance(this).use { realm ->
        val result = realm.where(clazz).findAll()
        return realm.copyFromRealm(result)
    }
}