package com.loperilla.register.dataSource

import com.loperilla.data.Auth.doFirebaseRegister
import com.loperilla.data.model.LoggedInUser
import com.loperilla.data.model.OnResult
import java.io.IOException

class RegisterDataSource {
    fun doRegister(email: String, password: String): OnResult<LoggedInUser> {
        return try {
            doFirebaseRegister(email, password)
        } catch (e: Throwable) {
            OnResult.Error<Any>(IOException("Error logging in", e))
        }
    }
}