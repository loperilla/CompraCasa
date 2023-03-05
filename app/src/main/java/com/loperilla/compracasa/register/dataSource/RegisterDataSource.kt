package com.loperilla.compracasa.register.dataSource

import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.data.result.OnResult
import com.loperilla.compracasa.data.result.ResultCallback
import com.loperilla.compracasa.firebase.auth.IFirebaseAuth

class RegisterDataSource(private val firebaseAuth: IFirebaseAuth) {
    suspend fun doRegister(dataRegistration: DataRegistration): OnResult<String> {
        var errorTask = ""
        firebaseAuth.doFirebaseRegister(
            dataRegistration,
            object : ResultCallback {
                override fun onSuccessfulResult() {
                    // ok
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