package com.example.newsapp.ui.base

sealed interface State {
    object None : State

    object Loading : State

    class Success(val any: Any? = null) : State

    class Failure(
        val error: Throwable,
        val repeat: () -> Unit,
    ) : State
}