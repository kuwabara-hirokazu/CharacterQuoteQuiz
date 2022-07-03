package com.example.characterquotequiz

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ActionResult<out T> {
    data class Success<T>(val data: T) : ActionResult<T>
    data class Error(val exception: Throwable? = null) : ActionResult<Nothing>
    object Loading : ActionResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ActionResult<T>> {
    return this
        .map<T, ActionResult<T>> {
            ActionResult.Success(it)
        }
        .onStart { emit(ActionResult.Loading) }
        .catch { emit(ActionResult.Error(it)) }
}