package com.loperilla.register.repository

import com.loperilla.register.dataSource.RegisterDataSource

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun doRegister(
        email: String,
        password: String
    ): com.loperilla.data.model.OnResult<com.loperilla.data.model.LoggedInUser> {
        // handle login
        return dataSource.doRegister(email, password)
    }
}