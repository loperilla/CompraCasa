package com.loperilla.compracasa.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.shoppinglist.data.InputsState
import com.loperilla.compracasa.shoppinglist.data.PostShoppingList
import com.loperilla.compracasa.shoppinglist.data.PostState
import com.loperilla.compracasa.shoppinglist.repository.AddShoppingRepository

class AddShoppingListViewModel(val repository: AddShoppingRepository) : ViewModel() {
    private val _isValidForm = MutableLiveData<InputsState>()
    val isValidFormState: LiveData<InputsState> = _isValidForm

    private val _postState = MutableLiveData<PostState>()
    val isPostSuccess: LiveData<PostState> = _postState

    fun inputsDataChange(input1: String, input2: String) {
        val areInputsNotEmpty = input1.isNotEmpty() && input2.isNotEmpty()

        _isValidForm.value = if (areInputsNotEmpty) {
            InputsState.CORRECT
        } else {
            InputsState.EMPTY
        }
    }

    fun addShoppingList(shoppingListItem: ShoppingListItem) {
        val postOnResult: OnResult<PostShoppingList> = repository.addShoppingList(shoppingListItem)

        _postState.value = if (postOnResult is OnResult.Success) {
            PostState.SUCCESS
        } else {
            PostState.FAIL
        }
    }
}
