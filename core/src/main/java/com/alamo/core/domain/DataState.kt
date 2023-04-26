package com.alamo.core.domain

sealed class DataState {

    object Loading: DataState()

    data class Success<T>(
        val data: T? = null
    ) : DataState()

    data class Error(
        val code: Int? = null,
        val description: String? = null
    ) : DataState()
}

