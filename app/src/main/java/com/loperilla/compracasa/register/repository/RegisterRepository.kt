package com.loperilla.compracasa.register.repository

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.LoggedInUser
import com.loperilla.compracasa.register.dataSource.RegisterDataSource

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun doRegister(email: String, password: String): OnResult<LoggedInUser> {
        // handle login
        return dataSource.doRegister(email, password)
    }
}