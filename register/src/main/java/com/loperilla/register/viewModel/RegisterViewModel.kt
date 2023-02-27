package com.loperilla.register.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loperilla.compracasa.R
import com.loperilla.compracasa.login.data.LoggedInUserView
import com.loperilla.register.model.RegisterFormState
import com.loperilla.register.model.RegisterResult
import com.loperilla.register.repository.RegisterRepository

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun registerFromDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
            return
        }
        if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            return
        }
        _registerForm.value = RegisterFormState(isDataValid = true)
    }

    fun doRegister(email: String, password: String) {
        val result = repository.doRegister(email, password)

        if (result is com.loperilla.data.model.OnResult.Success) {
            _registerResult.value =
                RegisterResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _registerResult.value = RegisterResult(error = R.string.login_failed)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}