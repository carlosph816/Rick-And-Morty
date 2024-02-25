package com.kairos.caperezh.network

import android.util.Log
import com.kairos.caperezh.R
import com.kairos.caperezh.common.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Suppress("TooGenericExceptionCaught")
suspend fun <T : Any?> safeCall(
    call: suspend () -> DataState<T>,
    errorMessage: Int = R.string.error_service
): Flow<DataState<T>> = flow {
    emit(DataState.Loading(true))
    try {
        emit(call())
    } catch (e: Exception) {
        Log.e("ERROR", e.message.toString())
        emit(DataState.Loading(false))
        emit(DataState.Error(errorMessage))
    }
}