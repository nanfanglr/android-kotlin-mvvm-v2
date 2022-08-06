package com.rui.mvvm.ui.firstchild

sealed class UiState<out R> {
    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
}