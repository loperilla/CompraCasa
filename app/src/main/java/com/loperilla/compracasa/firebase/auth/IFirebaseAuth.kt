package com.loperilla.compracasa.firebase.auth

import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.data.result.ResultCallback

interface IFirebaseAuth {
    suspend fun doTokenLoginFirebase(callback: ResultCallback)
    suspend fun doFirebaseLogin(email: String, password: String, callback: ResultCallback)
    suspend fun doFirebaseRegister(dataRegistration: DataRegistration, callback: ResultCallback)
}