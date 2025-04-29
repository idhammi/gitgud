package id.idham.gitgud.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.common.ViewModelState
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
                .onStart { update { copy(initialUsers = UiState.Loading) } }
                .catch { update { copy(initialUsers = UiState.Error(it.message)) } }
                .collect { users ->
                    if (users.isEmpty()) {
                        update { copy(initialUsers = UiState.Empty) }
                    } else {
                        update { copy(initialUsers = UiState.Success(users)) }
                    }
                }
        }
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch {
            repository.searchUsers(query)
                .onStart { update { copy(searchResult = UiState.Loading) } }
                .catch { update { copy(searchResult = UiState.Error(it.message)) } }
                .collect { users ->
                    if (users.isEmpty()) {
                        update { copy(searchResult = UiState.Empty) }
                    } else {
                        update { copy(searchResult = UiState.Success(users)) }
                    }
                }
        }
    }

    private var searchJob: Job? = null

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        if (query.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(400)
                sendAction(SearchAction.SubmitSearch)
            }
        }
    }
}
