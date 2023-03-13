package com.loperilla.compracasa.firebase.database

import com.loperilla.compracasa.data.model.IModel

interface IResultDatabaseCallback {
    fun onSuccessResult(resultList: List<IModel>)
    fun onFailureResult(message: String)
}