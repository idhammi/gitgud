package id.idham.gitgud.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import id.idham.gitgud.core.model.data.User
import id.idham.gitgud.core.ui.EmptyState
import id.idham.gitgud.core.ui.ErrorState
import id.idham.gitgud.core.ui.LoadingState
import id.idham.gitgud.core.ui.UiState
import id.idham.gitgud.core.ui.error.ErrorMessage

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onItemClicked: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchScreen(
        modifier = modifier,
        state = state,
        action = viewModel::sendAction,
        onItemClicked = onItemClicked
    )
}

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    action: (SearchAction) -> Unit,
    onItemClicked: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("search_input"),
                value = state.searchQuery,
                onValueChange = { action(SearchAction.SetQuery(it)) },
                label = { Text("Search") },
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onSearch = { keyboardController?.hide() }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            )
            val listUsers = if (state.searchQuery.isBlank()) {
                state.initialUsers
            } else {
                state.searchResult
            }
            when (listUsers) {
                is UiState.Loading -> LoadingState()
                is UiState.Error -> {
                    when (val error = listUsers.error) {
                        is ErrorMessage.Text -> ErrorState(error.message)
                        is ErrorMessage.Resource -> ErrorState(stringResource(error.resId))
                    }
                }

                is UiState.Empty -> EmptyState()
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listUsers.data, { user -> user.id }) { item ->
                            UserListItem(item) { onItemClicked(it.login) }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun UserListItem(
    item: User,
    onItemClicked: (User) -> Unit
) {
    Card(
        modifier = Modifier.testTag("user_item"),
        onClick = { onItemClicked(item) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.avatarUrl,
                contentDescription = item.login,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(item.login, style = MaterialTheme.typography.titleMedium)
                Text(item.htmlUrl, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreen_Preview() {
    val users = listOf(
        User(
            login = "Bambang",
            id = 1,
            avatarUrl = "https://example.com/avatar1.png",
            htmlUrl = "https://example.com/user1/html"
        ),
        User(
            login = "Bambang",
            id = 2,
            avatarUrl = "https://example.com/avatar1.png",
            htmlUrl = "https://example.com/user1/html"
        )
    )

    SearchScreen(
        state = SearchState(
            searchQuery = "",
            initialUsers = UiState.Success(users),
        ),
        action = { },
        onItemClicked = { }
    )
}
