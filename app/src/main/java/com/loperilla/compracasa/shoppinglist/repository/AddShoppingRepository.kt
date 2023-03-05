package com.loperilla.compracasa.shoppinglist.repository

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.shoppinglist.datasource.AddShoppingDataSource

class AddShoppingRepository(private val dataSource: AddShoppingDataSource) {
    fun addShoppingList(shoppingListItem: ShoppingListItem): OnResult<String> {
        return dataSource.addShoppingList(shoppingListItem)
    }

}
