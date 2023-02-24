package com.loperilla.compracasa.shoppinglist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loperilla.compracasa.shoppinglist.datasource.AddShoppingDataSource
import com.loperilla.compracasa.shoppinglist.repository.AddShoppingRepository


@Suppress("UNCHECKED_CAST")
class AddShoppingListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddShoppingListViewModel::class.java)) {
            return AddShoppingListViewModel(
                repository = AddShoppingRepository(
                    dataSource = AddShoppingDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}