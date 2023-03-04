package com.loperilla.compracasa.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.Extensions.mDataStore
import com.loperilla.compracasa.data.datastore.DataStoreRepositoryImpl
import com.loperilla.compracasa.firebase.auth.FirebaseAuthImpl
import com.loperilla.compracasa.firebase.auth.IFirebaseAuth
import com.loperilla.compracasa.firebase.database.IFirebaseDatabase
import com.loperilla.compracasa.firebase.database.ShoppingListRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        DataStoreRepositoryImpl(androidContext().mDataStore)
    }
    single<IFirebaseAuth> {
        FirebaseAuthImpl(get())
    }
    single<IFirebaseDatabase> {
        val dbInstance = Firebase.database
        ShoppingListRepository(dbInstance, get())
    }
}