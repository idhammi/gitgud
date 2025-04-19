package id.idham.gitgud.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.common.ViewModelState
import id.idham.gitgud.core.common.flow.mapToUiState
import id.idham.gitgud.core.data.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModelState<SearchState, SearchAction>(SearchState()) {

    init {
        sendAction(SearchAction.LoadInitialUsers)
    }

    override fun sendAction(action: SearchAction) {
        when (action) {
            is SearchAction.SetQuery -> {
                update { copy(searchQuery = action.query) }
                debounceSearch(action.query)
            }

            is SearchAction.SubmitSearch -> searchUsers(currentState().searchQuery)
            is SearchAction.LoadInitialUsers -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            repository.getUsers()
                .onStart { update { copy(isLoading = true, error = null) } }
                .catch { update { copy(isLoading = false, error = it.message) } }
                .collect { users ->
                    update { copy(isLoading = false, users = users, error = null) }
                }
        }
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch {
            repository.searchUsers(query)
                .mapToUiState()
                .collect { state ->
                    when (state) {
                        is UiState.Success -> update { copy(users = state.data, isLoading = false) }
                        is UiState.Error -> update {
                            copy(error = state.message, isLoading = false)
                        }

                        is UiState.Empty -> update { copy(users = emptyList(), isLoading = false) }
                        is UiState.Loading -> update { copy(isLoading = true) }
                    }
                }
        }
    }

    private var searchJob: Job? = null

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            sendAction(SearchAction.LoadInitialUsers)
            return
        }

        searchJob = viewModelScope.launch {
            delay(400)
            sendAction(SearchAction.SubmitSearch)
        }
    }
}
