package com.loperilla.compracasa.shoppinglist.repository

import com.loperilla.compracasa.data.Result
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
import com.loperilla.compracasa.shoppinglist.datasource.AddShoppingDataSource

class AddShoppingRepository(val dataSource: AddShoppingDataSource) {
    fun addShoppingList(shoppingListItem: ShoppingListItem): Result<PostShoppingList> {
        return dataSource.addShoppingList(shoppingListItem)
    }

}
