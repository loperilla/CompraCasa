package com.loperilla.compracasa.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override fun getString(key: String): Flow<String> {
        return dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)].orEmpty()
        }
    }

    override suspend fun insertString(key: String, value: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = value
        }
    }
}
