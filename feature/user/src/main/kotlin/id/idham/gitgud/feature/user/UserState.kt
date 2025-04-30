package id.idham.gitgud.feature.user

import id.idham.gitgud.core.model.data.User
import id.idham.gitgud.core.model.data.UserRepo
import id.idham.gitgud.core.ui.UiState

data class UserState(
    val user: UiState<User> = UiState.Idle,
    val repos: UiState<List<UserRepo>> = UiState.Idle
)
