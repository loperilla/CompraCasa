package com.loperilla.compracasa.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.Constants.PREFERENCES.KEY
import com.loperilla.compracasa.data.Constants.PREFERENCES.UUID_PREFERENCE
import com.loperilla.compracasa.data.OnResult
import com.loperilla.compracasa.data.datastore.DataStoreRepository
import com.loperilla.compracasa.data.model.DataRegistration
import kotlinx.coroutines.runBlocking


class FirebaseAuthImpl constructor(
    private val dataStore: DataStoreRepository
) : IFirebaseAuth {
    private val TAG = FirebaseAuthImpl::class.java.simpleName
    private val auth: FirebaseAuth
        get() = Firebase.auth

    private var currentAuthUser: FirebaseUser? = null
    val UID: String?
        get() = auth.uid


    override fun doTokenLoginFirebase(): OnResult<String> {
        var errorTask = ""
        runBlocking {
            auth.signInWithCustomToken(getDataStoreUserToken())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        currentAuthUser = auth.currentUser
                    } else {
                        errorTask = task.exception?.stackTraceToString() ?: "Hubo un error"
                    }
                }
        }
        return if (errorTask.isEmpty()) {
            OnResult.Success("")
        } else {
            OnResult.Error(errorTask)
        }
    }


    override fun doFirebaseLogin(email: String, password: String): OnResult<String> {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener({ }) { task ->
                Log.e(TAG, "${task.result}")
                if (task.isSuccessful) {
                    this.currentAuthUser = auth.currentUser
                    saveAuthToken()
                }
            }
        return if (auth.currentUser == null) {
            OnResult.Error("Error loginIn")
        } else {
            OnResult.Success(
                ""
            )
        }
    }

    override fun doFirebaseRegister(dataRegistration: DataRegistration): OnResult<String> {
        var taskError = ""
        auth.createUserWithEmailAndPassword(dataRegistration.email, dataRegistration.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentAuthUser = auth.currentUser
                    currentAuthUser!!.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(dataRegistration.displayName)
                            .build()
                    )
                } else {
                    taskError = task.exception?.stackTraceToString() ?: "Hubo un error"
                }
            }
        return if (taskError.isNotEmpty()) {
            OnResult.Success("")
        } else {
            OnResult.Error(taskError)
        }
    }

    private fun saveAuthToken() = runBlocking {
        var resultToken = ""
        currentAuthUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && !task.result.token.isNullOrEmpty()) {
                    resultToken = task.result.token!!
                }
            }
        if (resultToken.isNotEmpty()) {
            dataStore.insertString(KEY, resultToken)
        }
        UID?.let { dataStore.insertString(UUID_PREFERENCE, it) }
    }

    private suspend fun getDataStoreUserToken(): String {
        var token = ""
        dataStore.getString(KEY).collect {
            token = it
        }
        return token
    }
}
