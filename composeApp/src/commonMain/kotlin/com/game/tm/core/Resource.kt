package com.game.tm.core


sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int = 500) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String?,  code: Int = 500, data: T? = null): Resource<T>(data, message, code)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}