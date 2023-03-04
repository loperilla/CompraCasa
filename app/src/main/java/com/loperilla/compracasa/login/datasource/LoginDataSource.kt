package com.loperilla.compracasa.login.datasource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.LoggedInUser
import com.loperilla.compracasa.firebase.MyFirebaseAuth.doFirebaseLogin
import com.loperilla.compracasa.firebase.MyFirebaseAuth.doFirebaseLogout
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