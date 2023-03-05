package com.loperilla.compracasa.firebase.database

import com.google.firebase.database.DatabaseReference
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.IModel

interface IFirebaseDatabase {
    fun getAll(): OnResult<List<IModel>>

    //    fun getSingle(onCompleteGet: (OnResult<IModel>) -> Unit)
    fun insert(objectToInsert: IModel): OnResult<String>
    fun getDBReferenceByUID(): DatabaseReference
}
