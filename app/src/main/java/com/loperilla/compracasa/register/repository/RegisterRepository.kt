package com.loperilla.compracasa.register.repository

import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.register.dataSource.RegisterDataSource

class RegisterRepository(private val dataSource: RegisterDataSource) {
    suspend fun doRegister(dataRegister: DataRegistration): OnResult<String> {
        // handle login
        return dataSource.doRegister(dataRegister)
    }
}