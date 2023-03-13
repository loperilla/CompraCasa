package com.loperilla.compracasa.main.dataSource

import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.firebase.database.IFirebaseDatabase

class HomeDataSource(
    private val firebaseDatabase: IFirebaseDatabase
) {
    suspend fun getUserShoppingList(): List<IModel> = try {
        firebaseDatabase.getAll()
    } catch (ex: Exception) {
        ex.printStackTrace()
        listOf<IModel>()
    }

}