package com.loperilla.compracasa.register.model

import com.loperilla.compracasa.login.data.LoggedInUserView

data class RegisterResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)