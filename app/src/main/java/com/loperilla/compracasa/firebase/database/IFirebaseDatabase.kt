package com.loperilla.compracasa.firebase.database

import com.google.firebase.database.DatabaseReference
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.IModel

interface IFirebaseDatabase {
    fun getAll(onCompleteGet: (OnResult<List<IModel>>) -> Unit)

    //    fun getSingle(onCompleteGet: (OnResult<IModel>) -> Unit)
    fun insert(objectToInsert: IModel, onCompleteRegister: (OnResult<String>) -> Unit)
    fun getDBReferenceByUID(): DatabaseReference
}
