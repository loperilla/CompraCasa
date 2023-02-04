package com.loperilla.compracasa.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.Result
import com.loperilla.compracasa.data.model.LoggedInUser
import java.io.IOException

object Auth {
    private val TAG = Auth::class.java.simpleName
    private var auth: FirebaseAuth? = Firebase.auth
    private var currentAuthUser: FirebaseUser? = null
    fun checkIfIsUserLogged(): Boolean {
        return auth != null
    }

    fun doFirebaseLogin(username: String, password: String): Result<LoggedInUser> {
        checkAuthInstance()
        auth!!.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener({ }) { task ->
                Log.e(TAG, "${task.result}")
                this.currentAuthUser = auth?.currentUser
            }
        return if (auth?.currentUser == null) {
            Result.Error(IOException("Error loginIn", Exception()))
        } else {
            Result.Success(
                LoggedInUser(
                    auth?.currentUser?.uid!!,
                    auth?.currentUser?.email!!
                )
            )
        }
    }

    private fun checkAuthInstance() {
        if (auth == null) {
            auth = Firebase.auth
        }
    }

    fun doFirebaseRegister(email: String, password: String): Result<LoggedInUser> {
        checkAuthInstance()
        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener({ }) { task ->
            if (task.isSuccessful) {
                Log.e(TAG, "${task.result}")
            }
        }
        return doFirebaseLogin(email, password)
    }

    fun doFirebaseLogout() {
        if (auth != null && currentAuthUser != null) {
            auth?.signOut()
        }
    }
}
