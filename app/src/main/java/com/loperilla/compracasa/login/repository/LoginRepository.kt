package com.loperilla.compracasa.login.repository

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.login.datasource.LoginDataSource

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {
    suspend fun doLogin(username: String, password: String): OnResult<String> {
        return dataSource.doLogin(username, password)
    }
}