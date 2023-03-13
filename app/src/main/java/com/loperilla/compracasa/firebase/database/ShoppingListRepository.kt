package com.loperilla.compracasa.firebase.database

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.database.*
import com.loperilla.compracasa.data.Constants.DATABASE.ITEMS
import com.loperilla.compracasa.data.Constants.DATABASE.SHOPPINGLIST
import com.loperilla.compracasa.data.Constants.PREFERENCES.UUID_PREFERENCE
import com.loperilla.compracasa.data.datastore.IDataStore
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.data.result.ResultCallback
import kotlinx.coroutines.runBlocking

class ShoppingListRepository constructor(
    private val dbInstance: FirebaseDatabase,
    private val dataStore: IDataStore
) : IFirebaseDatabase {
    private val TAG = ShoppingListRepository::class.java.simpleName

    override suspend fun getAll(): List<IModel> = runBlocking {
        val returnList = mutableListOf<ShoppingListItem>()
        dataStore.getAll().collect { preferences ->
            val uid: String = preferences[stringPreferencesKey(UUID_PREFERENCE)]!!
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
        returnList
    }

    override suspend fun insertIModel(objectToInsert: IModel, callback: ResultCallback) {
        dataStore.getString(UUID_PREFERENCE).collect { uid ->
            val pushKey = getDBModelReference().child(uid).child(ITEMS).push().key
            getDBModelReference().child(uid).child(ITEMS).child(pushKey!!).setValue(
                objectToInsert
            ) { error, ref ->
                if (error != null) {
                    callback.onFailureResult(error.message)
                    return@setValue
                }
                callback.onSuccessfulResult()
            }
        }
    }

    override fun getDBModelReference(): DatabaseReference {
        return dbInstance.reference.child(SHOPPINGLIST)
    }

}
