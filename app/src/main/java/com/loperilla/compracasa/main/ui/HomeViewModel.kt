package com.loperilla.compracasa.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loperilla.compracasa.data.model.IModel
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.data.result.OnResult
import com.loperilla.compracasa.main.data.ShoppingState
import com.loperilla.compracasa.main.useCase.GetShoppingListUseCase
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val getShoppingUseCase: GetShoppingListUseCase
) : ViewModel() {

    private var _shoppingListState: MutableLiveData<ShoppingState> = MutableLiveData()
    val shoppingListState: LiveData<ShoppingState> = _shoppingListState

    private var _shoppingListItem: MutableLiveData<List<ShoppingListItem>> =
        MutableLiveData()
    val shoppingListItemLiveData: LiveData<List<ShoppingListItem>> =
        _shoppingListItem

    fun getUserShoppingList() {
        viewModelScope.launch {
            _shoppingListState.postValue(ShoppingState.LOADING)
            when (val result = getShoppingUseCase()) {
                is OnResult.Success<List<IModel>> -> {
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