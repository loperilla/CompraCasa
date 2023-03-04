package com.loperilla.compracasa.register.dataSource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.LoggedInUser
import java.io.IOException

class RegisterDataSource {
    fun doRegister(email: String, password: String): OnResult<LoggedInUser> {
        return try {
            MyFirebaseAuth.doFirebaseRegister(email, password)
        } catch (e: Throwable) {
            OnResult.Error<Any>(IOException("Error logging in", e))
        }
    }
}