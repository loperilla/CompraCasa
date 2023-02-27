package com.loperilla.register.model

data class RegisterResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)