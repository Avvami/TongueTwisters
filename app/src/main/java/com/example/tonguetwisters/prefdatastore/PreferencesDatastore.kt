package com.example.tonguetwisters.prefdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val USER_PREFERENCES = "USER_PREFERENCES"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

class PreferencesDatastore(context: Context) {

    private var preferences = context.dataStore

    companion object {
        var DARK_MODE = booleanPreferencesKey("DARK_MODE")
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        preferences.edit { preference ->
            preference[DARK_MODE] = isDarkMode
        }
    }

    val isDarkMode = preferences.data.map { preference ->
        preference[DARK_MODE] ?: false
    }
}