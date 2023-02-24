package com.loperilla.compracasa.shoppinglist.datasource

import com.loperilla.compracasa.data.Result
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.firebase.Database
import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
import java.io.IOException

class AddShoppingDataSource {
    fun addShoppingList(shoppingListItem: ShoppingListItem): Result<PostShoppingList> {
        val postShoppingList = Database.addShoppingList(shoppingListItem)
        return if (postShoppingList.isSuccess) {
            Result.Success(postShoppingList)
        } else {
            Result.Error(IOException(postShoppingList.errorMessage))
        }
    }
}