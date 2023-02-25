package com.loperilla.compracasa.shoppinglist.repository

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
import com.loperilla.compracasa.shoppinglist.datasource.AddShoppingDataSource

class AddShoppingRepository(val dataSource: AddShoppingDataSource) {
    fun addShoppingList(shoppingListItem: ShoppingListItem): OnResult<PostShoppingList> {
        return dataSource.addShoppingList(shoppingListItem)
    }

}
