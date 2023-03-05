package com.loperilla.compracasa.shoppinglist.usecase

import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.shoppinglist.repository.AddShoppingRepository

class AddShoppingUseCase(private val repository: AddShoppingRepository) {
    suspend operator fun invoke(shoppingList: ShoppingListItem) =
        repository.addShoppingList(shoppingList)
}