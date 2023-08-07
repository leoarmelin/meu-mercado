package com.leoarmelin.sharedmodels.api

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val exception: String): Result<Nothing>()
    object Loading: Result<Nothing>()
}