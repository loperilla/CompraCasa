package com.loperilla.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.loperilla.data.model.LoggedInUser
import com.loperilla.data.model.OnResult
import java.io.IOException

object Auth {
    private val TAG = Auth::class.java.simpleName
    private val auth: FirebaseAuth
        get() = Firebase.auth

    private var currentAuthUser: FirebaseUser? = null
    val UID: String?
        get() = auth.uid

    fun doFirebaseLogin(username: String, password: String): OnResult<LoggedInUser> {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener({ }) { task ->
                Log.e(TAG, "${task.result}")
                currentAuthUser = auth.currentUser
            }
        return if (auth.currentUser == null) {
            OnResult.Error<Any>(IOException("Error loginIn", Exception()))
        } else {
            OnResult.Success(
                LoggedInUser(
                    auth.currentUser?.uid!!,
                    auth.currentUser?.email!!
                )
            )
        }
    }

    fun doFirebaseRegister(email: String, password: String): OnResult<LoggedInUser> {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener({ }) { task ->
            if (task.isSuccessful) {
                Log.e(TAG, "${task.result}")
            }
        }
        return doFirebaseLogin(email, password)
    }

    fun doFirebaseLogout() {
        if (currentAuthUser != null) {
            auth.signOut()
        }
    }
}
