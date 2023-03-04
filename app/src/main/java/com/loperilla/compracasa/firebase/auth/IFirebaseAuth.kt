package com.loperilla.compracasa.firebase.auth

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.DataRegistration

interface IFirebaseAuth {
    fun doTokenLoginFirebase(): OnResult<String>
    fun doFirebaseLogin(email: String, password: String): OnResult<String>
    fun doFirebaseRegister(dataRegistration: DataRegistration): OnResult<String>
}