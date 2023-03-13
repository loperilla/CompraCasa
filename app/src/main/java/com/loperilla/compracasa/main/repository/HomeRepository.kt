package com.loperilla.compracasa.main.repository

import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.result.OnResult
import com.loperilla.compracasa.main.dataSource.HomeDataSource

class HomeRepository(private val dataSource: HomeDataSource) {
    suspend fun getUserShoppingList(): OnResult<List<IModel>> {
        val dsList = dataSource.getUserShoppingList()
        return if (dsList.isEmpty()) {
            OnResult.Error(dsList)
        } else {
            OnResult.Success(dsList)
        }
    }
}