package com.loperilla.compracasa.login.datasource

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.firebase.auth.IFirebaseAuth

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val firebaseAuth: IFirebaseAuth) {

    suspend fun doLogin(username: String, password: String): OnResult<String> {
        return firebaseAuth.doFirebaseLogin(username, password)
    }

}