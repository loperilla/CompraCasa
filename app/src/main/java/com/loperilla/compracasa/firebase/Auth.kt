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
        auth?.signInWithEmailAndPassword(username, password)
            ?.addOnCompleteListener({ }) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    this.currentAuthUser = auth?.currentUser
                } else {
                    Log.e(TAG, "${task.exception}")
                }
            }
        return if (this.currentAuthUser == null) {
            Result.Error(IOException("Error loginIn", Exception()))
        } else {
            Result.Success(
                LoggedInUser(
                    this.currentAuthUser?.uid!!,
                    this.currentAuthUser!!.displayName ?: ""
                )
            )
        }
    }


    fun doFirebaseLogout() {
        if (auth != null && currentAuthUser != null) {
            auth?.signOut()
        }
    }
}
