package id.idham.gitgud.core.ui.error

import androidx.annotation.StringRes

sealed class ErrorMessage {
    data class Text(val message: String) : ErrorMessage()
    data class Resource(@StringRes val resId: Int) : ErrorMessage()
}
