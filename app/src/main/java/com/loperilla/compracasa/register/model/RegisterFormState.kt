package com.loperilla.compracasa.register.model

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val displayNameError: Int? = null,
    val isDataValid: Boolean = false
)