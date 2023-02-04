package com.loperilla.compracasa.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {
    lateinit var database: FirebaseDatabase
    fun getFirstReference(name: String): DatabaseReference {
        if (!this::database.isInitialized) {
            database = Firebase.database
        }
        return database.getReference(name)
    }

    fun setValueInReference(reference: DatabaseReference, newValue: String) =
        reference.setValue(newValue)


}