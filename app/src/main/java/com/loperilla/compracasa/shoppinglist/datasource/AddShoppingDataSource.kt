package com.loperilla.compracasa.shoppinglist.datasource

import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.data.result.OnResult
import com.loperilla.compracasa.data.result.ResultCallback
import com.loperilla.compracasa.firebase.database.IFirebaseDatabase

class AddShoppingDataSource(
    private val firebaseDatabase: IFirebaseDatabase
) {
    suspend fun addShoppingList(shoppingListItem: ShoppingListItem): OnResult<String> {
        var errorMessage = ""
        firebaseDatabase.insertIModel(
            shoppingListItem,
            object : ResultCallback {
                override fun onSuccessfulResult() {
                    // ok
                }

                override fun onFailureResult(message: String) {
                    errorMessage = message
                }
            }
        )

        return if (errorMessage.isEmpty()) {
            OnResult.Success("")
        } else {
            OnResult.Error(errorMessage)
        }
    }
}