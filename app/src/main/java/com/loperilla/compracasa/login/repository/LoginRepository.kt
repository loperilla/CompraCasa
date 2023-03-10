package com.loperilla.compracasa.login.repository

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.LoggedInUser
import com.loperilla.compracasa.login.datasource.LoginDataSource

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.doLogout()
    }

    fun doLogin(username: String, password: String): OnResult<LoggedInUser> {
        // handle login
        val result = dataSource.doLogin(username, password)

        if (result is OnResult.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}