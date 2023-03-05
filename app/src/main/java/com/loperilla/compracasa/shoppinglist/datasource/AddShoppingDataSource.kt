package com.loperilla.compracasa.shoppinglist.datasource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.firebase.database.IFirebaseDatabase

class AddShoppingDataSource(
    private val firebaseDatabase: IFirebaseDatabase
) {
    fun addShoppingList(shoppingListItem: ShoppingListItem): OnResult<String> {
        return firebaseDatabase.insert(shoppingListItem)
    }
}