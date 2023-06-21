package com.alamo.core.domain

sealed class DataState {

    object Loading: DataState()

    data class Success<T>(
        val data: T? = null,
    ) : DataState()

    data class Error(
        val code: ErrorType,
        val description: String? = null,
    ) : DataState()

    enum class ErrorType {
        CONNECTION_PROBLEM,
        CONNECTION_SLOW,
        UNKNOWN,
    }
}

