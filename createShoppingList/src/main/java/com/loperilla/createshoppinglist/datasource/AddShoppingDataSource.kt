package com.loperilla.compracasa.shoppinglist.datasource

import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
import com.loperilla.data.Database
import com.loperilla.data.model.OnResult
import com.loperilla.data.model.ShoppingListItem
import java.io.IOException

class AddShoppingDataSource {
    fun addShoppingList(shoppingListItem: ShoppingListItem): OnResult<PostShoppingList> {
        val postShoppingList = Database.addShoppingList(shoppingListItem)
        return if (postShoppingList.isSuccess) {
            OnResult.Success(postShoppingList)
        } else {
            OnResult.Error<Any>(IOException(postShoppingList.errorMessage))
        }
    }
}