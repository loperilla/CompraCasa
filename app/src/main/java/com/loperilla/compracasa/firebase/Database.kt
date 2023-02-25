package com.loperilla.compracasa.firebase

import android.util.Log
import com.google.firebase.database.*
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


    fun getAllShoppingList(onComplete: (List<ShoppingListItem>) -> Unit) {
        var returnList = mutableListOf<ShoppingListItem>()
        SHOPPING_LIST_REFERENCE.child("items")
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (childSnapshot in dataSnapshot.children) {
                            val shoppingListItem =
                                childSnapshot.getValue(ShoppingListItem::class.java)
                            if (shoppingListItem != null) {
                                returnList.add(shoppingListItem)
                            }
                        }
                        onComplete(returnList)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e("ShoppingList", "Error: ${databaseError.message}")
                    }
                }
            )
    }

    fun addShoppingList(shoppingListItem: ShoppingListItem): PostShoppingList {
        var bbDDError = ""
        val pushKey = SHOPPING_LIST_REFERENCE.child("items").push().key
        SHOPPING_LIST_REFERENCE.child("items").child(pushKey!!).setValue(
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