package com.loperilla.compracasa.login.datasource

import com.loperilla.compracasa.data.result.OnResult
import com.loperilla.compracasa.data.result.ResultCallback
import com.loperilla.compracasa.firebase.auth.IFirebaseAuth

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val firebaseAuth: IFirebaseAuth) {
    suspend fun doLogin(username: String, password: String): OnResult<String> {
        var errorTask = ""
        firebaseAuth.doFirebaseLogin(
            username,
            password,
            object : ResultCallback {
                override fun onSuccessfulResult() {

                }

                override fun onFailureResult(message: String) {
                    errorTask = message
                }
            }
        )

        return if (errorTask.isEmpty()) {
            OnResult.Success("")
        } else {
            OnResult.Error(errorTask)
        }
    }

}