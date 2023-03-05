package com.loperilla.compracasa.register.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loperilla.compracasa.R
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.Validators.isEmailValid
import com.loperilla.compracasa.data.Validators.isPasswordValid
import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.register.model.RegisterFormState
import com.loperilla.compracasa.register.model.RegisterResult
import com.loperilla.compracasa.register.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun registerFromDataChanged(username: String, password: String, displayName: String) {
        if (!isEmailValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
            return
        }
        if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            return
        }
        if (displayName.isEmpty()) {
            _registerForm.value = RegisterFormState(displayNameError = R.string.display_name_empty)
            return
        }

        _registerForm.value = RegisterFormState(isDataValid = true)
    }

    fun doRegister(registration: DataRegistration) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.doRegister(registration)
            if (result is OnResult.Success) {
                _registerResult.postValue(RegisterResult.SUCCESSFULLY)
            } else {
                _registerResult.postValue(RegisterResult.FAIL)
            }
        }
    }
}