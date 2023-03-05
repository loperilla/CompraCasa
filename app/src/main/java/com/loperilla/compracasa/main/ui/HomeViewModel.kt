package com.loperilla.compracasa.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.data.result.OnResult
import com.loperilla.compracasa.main.data.ShoppingState
import com.loperilla.compracasa.main.useCase.GetShoppingListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel constructor(
    private val getShoppingUseCase: GetShoppingListUseCase
) : ViewModel() {

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
                when (val result = getShoppingUseCase()) {
                    is OnResult.Success<*> -> {
                        _shoppingListItem.postValue(result.data as List<ShoppingListItem>)
                        _shoppingListState.postValue(ShoppingState.SUCCESSFUL)
                    }

                    is OnResult.Error<*> -> {
                        _shoppingListState.postValue(ShoppingState.ERROR)
                    }
                }
            }
        }
    }
}