package com.loperilla.compracasa.firebase.database

import com.google.firebase.database.DatabaseReference
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.result.OnResult

interface IFirebaseDatabase {
    fun getAll(): OnResult<List<IModel>>

    //    fun getSingle(onCompleteGet: (OnResult<IModel>) -> Unit)
    fun insert(objectToInsert: IModel): OnResult<String>
    fun getDBModelReference(): DatabaseReference
}
