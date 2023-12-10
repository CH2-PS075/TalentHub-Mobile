package com.ch2ps075.talenthub.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(name = "language_setting")

class LanguagePreferences private constructor(val dataStore: DataStore<Preferences>) {

    private val languageKey = stringPreferencesKey("language_setting")

    fun getLanguageSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[languageKey] ?: "en"
        }
    }

    suspend fun saveLanguageSetting(languageCode: String) {
        dataStore.edit { preferences ->
            preferences[languageKey] = languageCode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LanguagePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): LanguagePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = LanguagePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}