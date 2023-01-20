package com.piashcse.experiment.mvvm_hilt.utils.network

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val msg: String?) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}