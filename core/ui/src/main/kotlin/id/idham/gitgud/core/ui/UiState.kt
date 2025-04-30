package id.idham.gitgud.core.ui

import id.idham.gitgud.core.ui.error.ErrorMessage

sealed interface UiState<out T> {
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: ErrorMessage) : UiState<Nothing>
    data object Empty : UiState<Nothing>
}
