package com.example.daytodaytest.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * Abstract shared preference. All prefs should be extended from this.
 */
abstract class AbstractSharedPref(context: Context, prefName: String) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    protected fun put(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    protected fun put(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    protected fun put(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    protected fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    protected fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }

    protected fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}