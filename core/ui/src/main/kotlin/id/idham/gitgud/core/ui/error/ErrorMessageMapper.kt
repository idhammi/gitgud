package id.idham.gitgud.core.ui.error

import id.idham.gitgud.core.model.ErrorType
import id.idham.gitgud.core.ui.R

object ErrorMessageMapper {

    fun from(error: ErrorType): ErrorMessage {
        return when (error) {
            is ErrorType.RateLimitExceeded -> ErrorMessage.Resource(R.string.error_rate_limit)
            is ErrorType.NetworkError -> ErrorMessage.Resource(R.string.error_network)
            is ErrorType.Unauthorized -> ErrorMessage.Resource(R.string.error_unauthorized)
            is ErrorType.NotFound -> ErrorMessage.Resource(R.string.error_not_found)
            is ErrorType.Generic -> ErrorMessage.Text(error.message)
        }
    }
}
