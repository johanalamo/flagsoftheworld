package com.alamo.core.domain

sealed class DataState {

    object Loading: DataState()

    data class Success<T>(
        val data: T? = null,
    ) : DataState()

    data class Error(
        val code: Int,
        val description: String,
    ) : DataState()
}

