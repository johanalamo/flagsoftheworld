package com.alamo.core.domain

sealed class DataState<T> {

//    object Uninitialized () : DataState<T>()
// try to change following to objects>

    // TODO: try to quit <T> from loading and

    data class Loading<T>(
        val isLoading: Boolean = false
    ) : DataState<T>()

    data class Success<T>(
        val data: T? = null
    ) : DataState<T>()

    data class Error<T>(
        val error: T? = null
    ) : DataState<T>()
}

