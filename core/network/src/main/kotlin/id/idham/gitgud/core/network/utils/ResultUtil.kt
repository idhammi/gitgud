package id.idham.gitgud.core.network.utils

import id.idham.gitgud.core.model.ErrorType
import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = apiCall()
        ResultWrapper.Success(result)
    } catch (throwable: Throwable) {
        ResultWrapper.Error(mapThrowableToErrorType(throwable))
    }
}

internal fun mapThrowableToErrorType(throwable: Throwable): ErrorType {
    return when (throwable) {
        is HttpException -> when (throwable.code()) {
            403 -> ErrorType.RateLimitExceeded
            401 -> ErrorType.Unauthorized
            404 -> ErrorType.NotFound
            else -> ErrorType.Generic(throwable.message())
        }

        is IOException -> ErrorType.NetworkError
        else -> ErrorType.Generic(throwable.message ?: "Unexpected error")
    }
}
