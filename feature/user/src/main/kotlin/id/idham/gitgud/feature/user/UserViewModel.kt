package id.idham.gitgud.feature.user

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.common.ViewModelState
import id.idham.gitgud.core.data.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = UserViewModel.Factory::class)
class UserViewModel @AssistedInject constructor(
    private val repository: UserRepository,
    @Assisted val username: String,
) : ViewModelState<UserState, UserAction>(UserState()) {

    init {
        sendAction(UserAction.Load)
    }

    override fun sendAction(action: UserAction) {
        when (action) {
            UserAction.Load -> {
                loadUser()
                loadRepos()
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            repository.getUser(username)
                .onStart { update { copy(user = UiState.Loading) } }
                .catch { update { copy(user = UiState.Error(it.message)) } }
                .collect { user ->
                    update {
                        copy(
                            user = if (user != null) {
                                UiState.Success(user)
                            } else {
                                UiState.Empty
                            }
                        )
                    }
                }
        }
    }

    private fun loadRepos() {
        viewModelScope.launch {
            repository.getUserRepo(username)
                .onStart { update { copy(user = UiState.Loading) } }
                .catch { update { copy(user = UiState.Error(it.message)) } }
                .collect { repos ->
                    if (repos.isEmpty()) {
                        update { copy(repos = UiState.Empty) }
                    } else {
                        update { copy(repos = UiState.Success(repos)) }
                    }
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(username: String): UserViewModel
    }
}
