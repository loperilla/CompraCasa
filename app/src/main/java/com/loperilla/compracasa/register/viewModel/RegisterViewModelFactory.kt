package com.loperilla.compracasa.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.loperilla.compracasa.register.dataSource.RegisterDataSource
import com.loperilla.compracasa.register.repository.RegisterRepository

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                repository = RegisterRepository(
                    dataSource = RegisterDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}