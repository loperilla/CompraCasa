package com.loperilla.compracasa.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loperilla.compracasa.R
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.Validators.isEmailValid
import com.loperilla.compracasa.data.Validators.isPasswordValid
import com.loperilla.compracasa.login.data.LoginFormState
import com.loperilla.compracasa.login.data.LoginResult
import com.loperilla.compracasa.login.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun doLoginApiCall(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginRepository.doLogin(username, password)

            if (result is OnResult.Success) {
                _loginResult.postValue(LoginResult.SUCCESSFULL)

            } else {
                _loginResult.postValue(LoginResult.FAIL)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isEmailValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            return
        }
        if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            return
        }
        _loginForm.value = LoginFormState(isDataValid = true)
    }
}