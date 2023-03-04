package com.loperilla.compracasa.firebase.database

import com.google.firebase.database.*
import com.loperilla.compracasa.data.Constants.DATABASE.ITEMS
import com.loperilla.compracasa.data.Constants.DATABASE.SHOPPINGLIST
import com.loperilla.compracasa.data.Constants.PREFERENCES.UUID_PREFERENCE
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.datastore.DataStoreRepository
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.model.ShoppingListItem
import kotlinx.coroutines.runBlocking

class ShoppingListRepository constructor(
    private val dbInstance: FirebaseDatabase,
    private val dataStore: DataStoreRepository
) : IFirebaseDatabase {
    private val TAG = ShoppingListRepository::class.java.simpleName
    private val UID: String
        get() = runBlocking {
            getDataStoreUserUID()
        }

    override fun getAll(onCompleteGet: (OnResult<List<IModel>>) -> Unit) {
        var returnList = mutableListOf<ShoppingListItem>()
        getDBReferenceByUID().child(ITEMS)
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
                        onCompleteGet(OnResult.Success(returnList))
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        onCompleteGet(OnResult.Error(listOf()))
                    }
                }
            )
    }

//    override fun getSingle(onCompleteGet: (OnResult<IModel>) -> Unit) {
//        TODO("Not yet implemented")
//    }

    override fun insert(objectToInsert: IModel, onCompleteRegister: (OnResult<String>) -> Unit) {
        val pushKey = getDBReferenceByUID().child(ITEMS).push().key
        getDBReferenceByUID().child(ITEMS).child(pushKey!!).setValue(
            objectToInsert
        ) { error, ref ->
            if (error != null) {
                onCompleteRegister(OnResult.Error(error.message))
                return@setValue
            }
            onCompleteRegister(OnResult.Success(""))
        }
    }

    override fun getDBReferenceByUID(): DatabaseReference {
        return dbInstance.reference.child(SHOPPINGLIST).child(UID)
    }

    private suspend fun getDataStoreUserUID(): String {
        var token = ""
        dataStore.getString(UUID_PREFERENCE).collect {
            token = it
        }
        return token
    }
}
