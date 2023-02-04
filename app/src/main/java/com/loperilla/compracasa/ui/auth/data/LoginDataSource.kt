package com.loperilla.compracasa.ui.auth.data

import com.loperilla.compracasa.data.Result
import com.loperilla.compracasa.firebase.Auth.doFirebaseLogin
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun doLogin(username: String, password: String): Result<String> {
        return try {
            doFirebaseLogin(username, password)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

}