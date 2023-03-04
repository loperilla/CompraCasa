package com.loperilla.compracasa.shoppinglist.datasource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
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