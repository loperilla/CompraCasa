package com.loperilla.compracasa.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.Extensions.mDataStore
import com.loperilla.compracasa.data.datastore.IDataStore
import com.loperilla.compracasa.data.datastore.IDataStoreImpl
import com.loperilla.compracasa.firebase.auth.FirebaseAuthImpl
import com.loperilla.compracasa.firebase.auth.IFirebaseAuth
import com.loperilla.compracasa.firebase.database.IFirebaseDatabase
import com.loperilla.compracasa.firebase.database.ShoppingListRepository
import com.loperilla.compracasa.login.datasource.LoginDataSource
import com.loperilla.compracasa.login.repository.LoginRepository
import com.loperilla.compracasa.login.viewmodel.LoginViewModel
import com.loperilla.compracasa.main.dataSource.HomeDataSource
import com.loperilla.compracasa.main.repository.HomeRepository
import com.loperilla.compracasa.main.ui.HomeViewModel
import com.loperilla.compracasa.main.useCase.GetShoppingListUseCase
import com.loperilla.compracasa.register.dataSource.RegisterDataSource
import com.loperilla.compracasa.register.repository.RegisterRepository
import com.loperilla.compracasa.register.viewModel.RegisterViewModel
import com.loperilla.compracasa.shoppinglist.datasource.AddShoppingDataSource
import com.loperilla.compracasa.shoppinglist.repository.AddShoppingRepository
import com.loperilla.compracasa.shoppinglist.usecase.AddShoppingUseCase
import com.loperilla.compracasa.shoppinglist.viewmodel.AddShoppingListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IDataStore> {
        IDataStoreImpl(androidContext().mDataStore)
    }
    factory<IFirebaseAuth> {
        FirebaseAuthImpl(get())
    }
    factory<IFirebaseDatabase> {
        val dbInstance = Firebase.database
        ShoppingListRepository(dbInstance, get())
    }
}

val loginModule = module {
    single {
        LoginDataSource(get())
    }
    single {
        LoginRepository(get())
    }

    viewModel {
        LoginViewModel(get())
    }
}

val registerModule = module {
    single {
        RegisterDataSource(get())
    }
    single {
        RegisterRepository(get())
    }

    viewModel {
        RegisterViewModel(get())
    }
}

val mainModule = module {
    single {
        HomeDataSource(get())
    }

    single {
        HomeRepository(get())
    }

    single {
        GetShoppingListUseCase(get())
    }

    viewModel {
        HomeViewModel(get())
    }
}

val addShoppingListModule = module {
    single {
        AddShoppingDataSource(get())
    }

    single {
        AddShoppingRepository(get())
    }

    single {
        AddShoppingUseCase(get())
    }

    viewModel {
        AddShoppingListViewModel(get())
    }
}