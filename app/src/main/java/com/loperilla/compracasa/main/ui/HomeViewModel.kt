package com.loperilla.compracasa.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.firebase.Database.getAllShoppingList
import com.loperilla.compracasa.main.data.ShoppingState

class HomeViewModel : ViewModel() {
    private var _shoppingListState: MutableLiveData<ShoppingState> = MutableLiveData()
    val shoppingListState: LiveData<ShoppingState> = _shoppingListState

    private var _shoppingListItem: MutableLiveData<FirebaseRecyclerOptions<ShoppingListItem>> =
        MutableLiveData()
    val shoppingListItemLiveData: LiveData<FirebaseRecyclerOptions<ShoppingListItem>> =
        _shoppingListItem

    init {
        _shoppingListState.value = ShoppingState.LOADING
        getUserShoppingList()
    }

    private fun getUserShoppingList() {
        val snapshotList = getAllShoppingList()
        if (snapshotList != null && snapshotList.snapshots.isEmpty()) {
            _shoppingListState.value = ShoppingState.ERROR
            return
        }

        _shoppingListItem.value = snapshotList!!
        _shoppingListState.value = ShoppingState.SUCCESSFUL
    }
}