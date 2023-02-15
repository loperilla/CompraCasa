package com.loperilla.compracasa.firebase

import android.util.Log
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.firebase.Auth.UID

object Database {
    private const val SHOPPING_LIST = "shoppingList"
    private val INSTANCE_DB: FirebaseDatabase
        get() = Firebase.database

    fun getAllShoppingList(): FirebaseRecyclerOptions<ShoppingListItem>? {
        val query = UID?.let { INSTANCE_DB.reference.child(SHOPPING_LIST).child(it) }
        Log.e("Database", "$query")
        val options = query?.let {
            FirebaseRecyclerOptions.Builder<ShoppingListItem>()
                .setQuery(it, ShoppingListItem::class.java)
                .build()
        }
        if (options != null) {
            Log.e("Database", "${options.snapshots}")
        }
        return options
    }
}