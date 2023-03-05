package com.loperilla.compracasa.data.result

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class OnResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : OnResult<T>()
    data class Error<out U : Any>(val exception: U) : OnResult<U>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error<*> -> "Error[exception=$exception]"
        }
    }
}