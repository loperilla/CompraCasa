package com.loperilla.compracasa.firebase.database

import android.util.Log
import com.google.firebase.database.*
import com.loperilla.compracasa.data.Constants.DATABASE.ITEMS
import com.loperilla.compracasa.data.Constants.DATABASE.SHOPPINGLIST
import com.loperilla.compracasa.data.Constants.PREFERENCES.UUID_PREFERENCE
import com.loperilla.compracasa.data.datastore.IDataStore
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.data.result.OnResult
import kotlinx.coroutines.runBlocking

class ShoppingListRepository constructor(
    private val dbInstance: FirebaseDatabase,
    private val dataStore: IDataStore
) : IFirebaseDatabase {
    private val TAG = ShoppingListRepository::class.java.simpleName

    override fun getAll(): OnResult<List<IModel>> {
        var returnList = mutableListOf<ShoppingListItem>()
        runBlocking {
            dataStore.getString(UUID_PREFERENCE).collect { uid ->
                getDBModelReference().child(uid).child(ITEMS)
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
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.e(TAG, databaseError.message)
                            }
                        }
                    )
            }
        }


        return if (returnList.isEmpty()) {
            OnResult.Error(listOf())
        } else {
            OnResult.Success(returnList)
        }
    }

//    override fun getSingle(onCompleteGet: (OnResult<IModel>) -> Unit) {
//        TODO("Not yet implemented")
//    }

    override fun insert(objectToInsert: IModel): OnResult<String> {
        var errorMessage = ""
        runBlocking {
            dataStore.getString(UUID_PREFERENCE).collect { uid ->
                val pushKey = getDBModelReference().child(uid).child(ITEMS).push().key
                getDBModelReference().child(ITEMS).child(uid).child(pushKey!!).setValue(
                    objectToInsert
                ) { error, ref ->
                    if (error != null) {
                        errorMessage = error.message
                        return@setValue
                    }
                }
            }
        }
        return if (errorMessage.isEmpty()) {
            OnResult.Success("")
        } else {
            OnResult.Error(errorMessage)
        }
    }

    override fun getDBModelReference(): DatabaseReference {
        return dbInstance.reference.child(SHOPPINGLIST)
    }
}
