package com.loperilla.compracasa

import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp

class App : MultiDexApplication() {
    lateinit var instance: App

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
    }
}