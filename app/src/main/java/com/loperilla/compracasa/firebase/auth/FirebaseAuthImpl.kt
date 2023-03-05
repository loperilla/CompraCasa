package com.loperilla.compracasa.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.loperilla.compracasa.data.Constants.PREFERENCES.KEY
import com.loperilla.compracasa.data.Constants.PREFERENCES.UUID_PREFERENCE
import com.loperilla.compracasa.data.datastore.IDataStore
import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.data.result.ResultCallback
import kotlinx.coroutines.runBlocking

class FirebaseAuthImpl constructor(
    private val dataStore: IDataStore
) : IFirebaseAuth {
    private val TAG = FirebaseAuthImpl::class.java.simpleName
    private val auth: FirebaseAuth
        get() = Firebase.auth

    private var currentAuthUser: FirebaseUser? = null
    val UID: String?
        get() = auth.uid


    override suspend fun doTokenLoginFirebase(callback: ResultCallback) = runBlocking {
        dataStore.getString(KEY).collect { token ->
            if (token.isEmpty()) {
                callback.onFailureResult("Empty token")
                return@collect
            }
            auth.signInWithCustomToken(token)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        currentAuthUser = auth.currentUser
                        callback.onSuccessfulResult()
                    } else {
                        callback.onFailureResult(
                            task.exception?.stackTraceToString() ?: "Hubo un error"
                        )
                    }
                }
        }
    }

    override suspend fun doFirebaseLogin(
        email: String,
        password: String,
        callback: ResultCallback
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    this.currentAuthUser = auth.currentUser
                    saveAuthToken(callback)
                }
            }
            .addOnFailureListener {
                callback.onFailureResult(it.message ?: "Hubo un error")
            }
    }

    override suspend fun doFirebaseRegister(
        dataRegistration: DataRegistration,
        callback: ResultCallback
    ) {
        auth.createUserWithEmailAndPassword(dataRegistration.email, dataRegistration.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentAuthUser = auth.currentUser
                    currentAuthUser!!.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(dataRegistration.displayName)
                            .build()
                    )
                    callback.onSuccessfulResult()
                } else {
                    task.exception?.printStackTrace()
                    callback.onFailureResult(
                        task.exception?.stackTraceToString() ?: "Hubo un error"
                    )
                }
            }
    }

    private fun saveAuthToken(callback: ResultCallback) = runBlocking {
        currentAuthUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && !task.result.token.isNullOrEmpty()) {
                    dataStore.insertString(KEY, task.result.token!!)
                    callback.onSuccessfulResult()
                }
                callback.onFailureResult("Hubo un error")
            }
            .addOnFailureListener {
                callback.onFailureResult(it.message ?: "Hubo un error")
            }

        UID?.let { dataStore.insertString(UUID_PREFERENCE, it) }
    }
}
