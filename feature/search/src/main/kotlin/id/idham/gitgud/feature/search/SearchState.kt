package id.idham.gitgud.feature.search

import id.idham.gitgud.core.model.SimpleUser

data class SearchState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val users: List<SimpleUser> = emptyList(),
    val error: String? = null
)
