package com.loperilla.compracasa.main.useCase

import com.loperilla.compracasa.main.repository.HomeRepository

class GetShoppingListUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke() = repository.getUserShoppingList()
}