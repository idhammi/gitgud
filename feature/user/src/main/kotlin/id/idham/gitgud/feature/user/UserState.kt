package id.idham.gitgud.feature.user

import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.model.User
import id.idham.gitgud.core.model.UserRepo

data class UserState(
    val user: UiState<User> = UiState.Loading,
    val repos: UiState<List<UserRepo>> = UiState.Loading
)
