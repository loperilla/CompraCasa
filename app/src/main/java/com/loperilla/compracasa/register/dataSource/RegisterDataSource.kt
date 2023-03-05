package com.loperilla.compracasa.register.dataSource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.firebase.auth.IFirebaseAuth

class RegisterDataSource(private val firebaseAuth: IFirebaseAuth) {
    suspend fun doRegister(dataRegistration: DataRegistration): OnResult<String> {
        return firebaseAuth.doFirebaseRegister(dataRegistration)
    }
}