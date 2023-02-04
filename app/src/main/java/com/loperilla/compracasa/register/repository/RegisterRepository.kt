package com.loperilla.compracasa.register.repository

import com.loperilla.compracasa.data.Result
import com.loperilla.compracasa.data.model.LoggedInUser
import com.loperilla.compracasa.register.dataSource.RegisterDataSource

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun doRegister(email: String, password: String): Result<LoggedInUser> {
        // handle login
        return dataSource.doRegister(email, password)
    }
}