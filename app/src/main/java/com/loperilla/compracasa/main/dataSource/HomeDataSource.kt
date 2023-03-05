package com.loperilla.compracasa.main.dataSource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.firebase.database.IFirebaseDatabase

class HomeDataSource(
    private val firebaseDatabase: IFirebaseDatabase
) {
    suspend fun getUserShoppingList(): OnResult<List<IModel>> {
        return firebaseDatabase.getAll()
    }
}