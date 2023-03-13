package com.loperilla.compracasa.data.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface IDataStore {
    fun getAll(): Flow<Preferences>
    fun getString(key: String): Flow<String>
    fun insertString(key: String, value: String)
    fun printValues()
}