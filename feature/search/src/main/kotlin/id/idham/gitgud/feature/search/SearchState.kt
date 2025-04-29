package id.idham.gitgud.feature.search

import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.model.User

data class SearchState(
    val searchQuery: String = "",
    val initialUsers: UiState<List<User>> = UiState.Idle,
    val searchResult: UiState<List<User>> = UiState.Idle,
)
