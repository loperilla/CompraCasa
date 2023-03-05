package com.loperilla.compracasa.data.result

interface ResultCallback {
    fun onSuccessfulResult()
    fun onFailureResult(message: String)
}