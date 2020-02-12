package com.example.daytodaytest.dashboard.database

import io.realm.DynamicRealm
import io.realm.RealmMigration
import java.util.*

/**
 * This class provides realm migration rule and new fields for database update.
 */
class DashboardDatabaseMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm, old: Long, newVersion: Long) {
        var oldVersion: Long = old

        // Migrate to version 1:
        if (oldVersion == 1L) {
            // We don't have to update any scheme for this version.
            oldVersion++
        }

        // Migrate to version 2:
        if (oldVersion == 2L) {
            // Update the schema here.
            oldVersion++
        }

        if (oldVersion < newVersion) {
            throw IllegalStateException(
                String.format(
                    Locale.US,
                    "Migration missing from v%d to v%d",
                    oldVersion,
                    newVersion
                )
            )
        }
    }

}