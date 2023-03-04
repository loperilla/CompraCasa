package com.loperilla.compracasa.data.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getString(key: String): Flow<String>
    suspend fun insertString(key: String, value: String)
}