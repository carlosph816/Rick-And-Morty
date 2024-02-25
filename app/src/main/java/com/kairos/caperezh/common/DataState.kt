package com.kairos.caperezh.common

sealed class DataState<out T : Any?> {
    data class Loading(val isLoading: Boolean) : DataState<Nothing>()
    data class Success<out T: Any>(val data: T): DataState<T>()
    data class Error(val exception: Int, val errorCode: Int = 0) : DataState<Nothing>()
}