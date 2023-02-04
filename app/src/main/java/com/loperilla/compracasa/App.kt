package com.loperilla.compracasa

import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class App : MultiDexApplication() {
    lateinit var instance: App
    lateinit var authUser: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
    }
}