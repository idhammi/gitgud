package id.idham.gitgud.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.idham.gitgud.core.common.ViewModelState
import id.idham.gitgud.core.data.repository.UserRepository
import id.idham.gitgud.core.ui.utils.toUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
            repository.getUsers().collect { result ->
                update {
                    copy(initialUsers = result.toUiState { it.isEmpty() })
                }
            }
        }
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch {
            repository.searchUsers(query).collect { result ->
                update {
                    copy(searchResult = result.toUiState { it.isEmpty() })
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
