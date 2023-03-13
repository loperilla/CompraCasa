package com.loperilla.compracasa.firebase.database

import com.google.firebase.database.DatabaseReference
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.result.ResultCallback

interface IFirebaseDatabase {
    suspend fun getAll(): List<IModel>

    //    fun getSingle(onCompleteGet: (OnResult<IModel>) -> Unit)
    suspend fun insertIModel(objectToInsert: IModel, callback: ResultCallback)
    fun getDBModelReference(): DatabaseReference
}
