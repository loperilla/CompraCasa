package com.loperilla.compracasa.firebase

import android.util.Log
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.firebase.Auth.UID
import com.loperilla.compracasa.shoppinglist.data.PostShoppingList

object Database {
    private const val SHOPPING_LIST = "shoppingList"
    private val INSTANCE_DB: FirebaseDatabase
        get() = Firebase.database

    private val SHOPPING_LIST_REFERENCE: DatabaseReference
        get() = INSTANCE_DB.reference.child(SHOPPING_LIST).child(UID!!)


    fun getAllShoppingList(): FirebaseRecyclerOptions<ShoppingListItem>? {
        val options =
            FirebaseRecyclerOptions.Builder<ShoppingListItem>()
                .setQuery(SHOPPING_LIST_REFERENCE, ShoppingListItem::class.java)
                .build()
        Log.e("Database", "${options.snapshots}")
        return options
    }

    fun addShoppingList(shoppingListItem: ShoppingListItem): PostShoppingList {
        var bbDDError = ""
        SHOPPING_LIST_REFERENCE.setValue(
            shoppingListItem
        ) { error, ref ->
            if (error != null) {
                bbDDError = error.message
                Log.e("Database", "$error")
            }
        }

        return if (bbDDError.isEmpty()) {
            PostShoppingList(isSuccess = true)
        } else {
            PostShoppingList(errorMessage = bbDDError)
        }
    }
}