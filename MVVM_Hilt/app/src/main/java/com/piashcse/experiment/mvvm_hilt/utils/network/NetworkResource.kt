package com.piashcse.experiment.mvvm_hilt.utils.network

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: suspend (ResultType) -> Boolean = { true }
) = flow {
    emit(DataState.Loading)
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(DataState.Loading)
        try {
            saveFetchResult(fetch())
            query().map { DataState.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { DataState.Error(throwable.message) }
        }
    } else {
        query().map { DataState.Success(it) }
    }
    emitAll(flow)
}