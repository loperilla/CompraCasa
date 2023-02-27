package com.loperilla.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loperilla.compracasa.R
import com.loperilla.compracasa.login.data.LoggedInUserView
import com.loperilla.compracasa.login.data.LoginFormState
import com.loperilla.compracasa.login.data.LoginResult
import com.loperilla.login.repository.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun doLoginApiCall(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.doLogin(username, password)

        if (result is com.loperilla.data.model.OnResult.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            return
        }
        if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            return
        }
        _loginForm.value = LoginFormState(isDataValid = true)
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