package com.loperilla.compracasa.data.datastore

import kotlinx.coroutines.flow.Flow

interface IDataStore {
    fun getString(key: String): Flow<String>
    fun insertString(key: String, value: String)
    fun printValues()
}