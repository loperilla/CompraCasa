package com.loperilla.compracasa

import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.loperilla.compracasa.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : MultiDexApplication() {
    lateinit var instance: App

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModule)
        }
    }
}