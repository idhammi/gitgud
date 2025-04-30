package id.idham.gitgud.core.model

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: ErrorType) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}
