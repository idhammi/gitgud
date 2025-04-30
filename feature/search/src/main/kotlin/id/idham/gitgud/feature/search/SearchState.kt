package id.idham.gitgud.feature.search

import id.idham.gitgud.core.model.data.User
import id.idham.gitgud.core.ui.UiState

data class SearchState(
    val searchQuery: String = "",
    val initialUsers: UiState<List<User>> = UiState.Idle,
    val searchResult: UiState<List<User>> = UiState.Idle,
)
