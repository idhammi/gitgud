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
        if (currentState().searchQuery.isBlank()) {
            sendAction(SearchAction.LoadInitialUsers)
        }
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
                .onStart { update { copy(users = UiState.Loading) } }
                .catch { update { copy(users = UiState.Error(it.message)) } }
                .collect { users ->
                    if (users.isEmpty()) {
                        update { copy(users = UiState.Empty) }
                    } else {
                        update { copy(users = UiState.Success(users)) }
                    }
                }
        }
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch {
            repository.searchUsers(query)
                .onStart { update { copy(users = UiState.Loading) } }
                .catch { update { copy(users = UiState.Error(it.message)) } }
                .collect { users ->
                    if (users.isEmpty()) {
                        update { copy(users = UiState.Empty) }
                    } else {
                        update { copy(users = UiState.Success(users)) }
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
