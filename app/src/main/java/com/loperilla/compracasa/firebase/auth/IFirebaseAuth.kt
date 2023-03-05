package com.loperilla.compracasa.firebase.auth

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.DataRegistration

interface IFirebaseAuth {
    suspend fun doTokenLoginFirebase(): OnResult<String>
    suspend fun doFirebaseLogin(email: String, password: String): OnResult<String>
    suspend fun doFirebaseRegister(dataRegistration: DataRegistration): OnResult<String>
}