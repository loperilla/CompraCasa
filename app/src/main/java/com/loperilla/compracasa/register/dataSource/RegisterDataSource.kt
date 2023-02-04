package com.loperilla.compracasa.register.dataSource

import com.loperilla.compracasa.data.Result
import com.loperilla.compracasa.data.model.LoggedInUser
import com.loperilla.compracasa.firebase.Auth
import java.io.IOException

class RegisterDataSource {
    fun doRegister(email: String, password: String): Result<LoggedInUser> {
        return try {
            Auth.doFirebaseRegister(email, password)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}