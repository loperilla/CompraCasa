package com.loperilla.login.datasource

import com.loperilla.data.Auth.doFirebaseLogin
import com.loperilla.data.Auth.doFirebaseLogout
import com.loperilla.data.model.LoggedInUser
import com.loperilla.data.model.OnResult
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun doLogin(username: String, password: String): OnResult<LoggedInUser> {
        return try {
            doFirebaseLogin(username, password)
        } catch (e: Throwable) {
            OnResult.Error<Any>(IOException("Error logging in", e))
        }
    }

    fun doLogout() {
        doFirebaseLogout()
    }
}