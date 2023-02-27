package com.loperilla.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loperilla.data.Database.getAllShoppingList
import com.loperilla.home.data.ShoppingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private var _shoppingListState: MutableLiveData<ShoppingState> = MutableLiveData()
    val shoppingListState: LiveData<ShoppingState> = _shoppingListState

    private var _shoppingListItem: MutableLiveData<List<com.loperilla.data.model.ShoppingListItem>> =
        MutableLiveData()
    val shoppingListItemLiveData: LiveData<List<com.loperilla.data.model.ShoppingListItem>> =
        _shoppingListItem

    init {
        _shoppingListState.value = ShoppingState.LOADING
    }

    fun getUserShoppingList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getAllShoppingList { result ->
                    if (result is com.loperilla.data.model.OnResult.Success<*>) {
                        _shoppingListItem.postValue(result.data as List<com.loperilla.data.model.ShoppingListItem>)
                        _shoppingListState.value = ShoppingState.SUCCESSFUL
                        return@getAllShoppingList
                    }
                    _shoppingListState.value = ShoppingState.ERROR
                }
            }
        }
    }
}