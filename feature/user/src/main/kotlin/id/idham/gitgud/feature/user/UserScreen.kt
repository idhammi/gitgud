package id.idham.gitgud.feature.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.model.User
import id.idham.gitgud.core.model.UserRepo
import id.idham.gitgud.core.ui.EmptyState
import id.idham.gitgud.core.ui.ErrorState
import id.idham.gitgud.core.ui.LoadingState

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UserScreen(
        modifier = modifier,
        state = state
    )
}

@Composable
internal fun UserScreen(
    modifier: Modifier = Modifier,
    state: UserState
) {
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // user detail
            when (state.user) {
                is UiState.Loading -> item { LoadingState() }
                is UiState.Error -> item { ErrorState(state.user.message) }
                is UiState.Empty -> item { EmptyState() }
                is UiState.Success -> item { UserDetail(state.user.data) }
                else -> {}
            }
            // user repos
            item {
                Text(
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                    text = "Repositories",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            when (state.repos) {
                is UiState.Loading -> item { LoadingState() }
                is UiState.Success -> {
                    items(state.repos.data, key = { item -> item.id }) { repo ->
                        UserRepoItem(
                            modifier = Modifier.padding(bottom = 8.dp),
                            repo = repo
                        )
                    }
                }

                else -> item { Text("No data found") }
            }
        }

    }

}

@Composable
fun UserDetail(user: User) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier.testTag("user_detail_name"),
            text = user.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "@${user.login}",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.height(8.dp))
        if (user.bio.isNotBlank()) Text(text = user.bio)
        if (user.location.isNotBlank()) Text(text = "📍 ${user.location}")
        Spacer(Modifier.height(8.dp))
        Text(text = "Followers: ${user.followers} | Following: ${user.following}")
        Text(text = "Public repos: ${user.publicRepos}")
    }
}

@Composable
private fun UserRepoItem(
    modifier: Modifier = Modifier,
    repo: UserRepo
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = repo.language.orEmpty(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun UserScreen_Preview() {
    val repos = listOf(
        UserRepo(
            id = 1,
            name = "Android Studio",
            description = "Description here",
            language = "Kotlin",
            stargazersCount = 0,
            updatedAt = "2025"
        ),
        UserRepo(
            id = 2,
            name = "Android Studio",
            description = "Description here",
            language = "Kotlin",
            stargazersCount = 0,
            updatedAt = "2025"
        )
    )

    UserScreen(
        state = UserState(
            user = UiState.Success(
                User(
                    login = "idham",
                    name = "Idham",
                    avatarUrl = "",
                    id = 1,
                    htmlUrl = "https://github.com/idhammi",
                    bio = "Software Engineer",
                    location = "Jakarta, Indonesia",
                    followers = 10,
                    following = 10,
                    publicRepos = 10
                )
            ),
            repos = UiState.Success(repos)
        )
    )
}
