package com.alamo.core.domain

sealed class DataState {

    object Loading: DataState()

    data class Success<T>(
        val data: T? = null,
    ) : DataState()

    data class Error(
        val code: ErrorType,
    ) : DataState()

    enum class ErrorType {
        CONNECTION_ERROR,
        CONNECTION_SLOW,
        UNKNOWN,
    }
}

