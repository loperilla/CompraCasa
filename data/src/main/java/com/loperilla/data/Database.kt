package com.loperilla.data

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.loperilla.data.Auth.UID
import com.loperilla.data.model.OnResult
import com.loperilla.data.model.OnResult.Error
import com.loperilla.data.model.OnResult.Success
import com.loperilla.data.model.ShoppingListItem
import java.io.IOException

object Database {
    private const val SHOPPING_LIST = "shoppingList"
    private val INSTANCE_DB: FirebaseDatabase
        get() = Firebase.database

    private val SHOPPING_LIST_REFERENCE: DatabaseReference
        get() = INSTANCE_DB.reference.child(SHOPPING_LIST).child(UID!!)


    fun getAllShoppingList(onComplete: (OnResult<*>) -> Unit) {
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
                        onComplete(Success(returnList))
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        onComplete(Error<String>(exception = IOException(databaseError.message)))
                    }
                }
            )
    }

//    fun addShoppingList(shoppingListItem: ShoppingListItem): PostShoppingList {
//        var bbDDError = ""
//        val pushKey = SHOPPING_LIST_REFERENCE.child("items").push().key
//        SHOPPING_LIST_REFERENCE.child("items").child(pushKey!!).setValue(
//            shoppingListItem
//        ) { error, ref ->
//            if (error != null) {
//                bbDDError = error.message
//                Log.e("Database", "$error")
//            }
//        }
//
//        return if (bbDDError.isEmpty()) {
//            PostShoppingList(isSuccess = true)
//        } else {
//            PostShoppingList(errorMessage = bbDDError)
//        }
//    }
}