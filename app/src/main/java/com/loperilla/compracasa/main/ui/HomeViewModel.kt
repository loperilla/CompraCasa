package com.loperilla.compracasa.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.firebase.Database.getAllShoppingList
import com.loperilla.compracasa.main.data.ShoppingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private var _shoppingListState: MutableLiveData<ShoppingState> = MutableLiveData()
    val shoppingListState: LiveData<ShoppingState> = _shoppingListState

    private var _shoppingListItem: MutableLiveData<List<ShoppingListItem>> =
        MutableLiveData()
    val shoppingListItemLiveData: LiveData<List<ShoppingListItem>> =
        _shoppingListItem

    init {
        _shoppingListState.value = ShoppingState.LOADING
    }

    fun getUserShoppingList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getAllShoppingList { list ->
                    _shoppingListItem.postValue(list)
                    _shoppingListState.value = ShoppingState.SUCCESSFUL
                }
            }
        }
    }
}