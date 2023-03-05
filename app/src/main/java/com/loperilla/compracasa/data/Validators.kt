package com.loperilla.compracasa.data

import android.util.Patterns

object Validators {
    fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String) = password.length > 5

}
