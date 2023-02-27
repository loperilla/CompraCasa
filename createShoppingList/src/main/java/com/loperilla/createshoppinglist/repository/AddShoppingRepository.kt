package com.loperilla.compracasa.shoppinglist.repository

import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
import com.loperilla.compracasa.shoppinglist.datasource.AddShoppingDataSource
import com.loperilla.data.model.OnResult
import com.loperilla.data.model.ShoppingListItem

class AddShoppingRepository(val dataSource: AddShoppingDataSource) {
    fun addShoppingList(shoppingListItem: ShoppingListItem): OnResult<PostShoppingList> {
        return dataSource.addShoppingList(shoppingListItem)
    }

}
