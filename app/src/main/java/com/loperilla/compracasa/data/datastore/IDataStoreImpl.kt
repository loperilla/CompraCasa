package com.loperilla.compracasa.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class IDataStoreImpl constructor(
    private val dataStore: DataStore<Preferences>
) : IDataStore {
    private val TAG = IDataStoreImpl::class.java.simpleName
    override fun getAll(): Flow<Preferences> {
        return dataStore.data
    }

    override fun getString(key: String): Flow<String> {
        return dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)].orEmpty()
        }
    }

    override fun insertString(key: String, value: String): Unit = runBlocking {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = value
        }
        printValues()
    }

    override fun printValues(): Unit = runBlocking {
        dataStore.data.map {
            it.asMap().forEach { (key, value) ->
                Log.e(TAG, "$key - $value")
            }
        }
    }
}
