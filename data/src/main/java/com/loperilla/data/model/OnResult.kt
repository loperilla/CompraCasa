package com.loperilla.data.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class OnResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : OnResult<T>()
    data class Error<U>(val exception: Exception) : OnResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error<*> -> "Error[exception=$exception]"
        }
    }
}