package id.idham.gitgud.feature.user

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import id.idham.gitgud.core.common.ViewModelState
import id.idham.gitgud.core.data.repository.UserRepository
import id.idham.gitgud.core.ui.utils.toUiState
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
            repository.getUser(username).collect { user ->
                update {
                    copy(user = user.toUiState())
                }
            }
        }
    }

    private fun loadRepos() {
        viewModelScope.launch {
            repository.getUserRepo(username).collect { repos ->
                update {
                    copy(repos = repos.toUiState { it.isEmpty() })
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(username: String): UserViewModel
    }
}
