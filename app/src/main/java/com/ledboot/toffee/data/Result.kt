package com.ledboot.toffee.data

sealed class Result<out R> {

    object Loading : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

}

val Result<*>.succeeded
    get() = this is Result.Success && data != null