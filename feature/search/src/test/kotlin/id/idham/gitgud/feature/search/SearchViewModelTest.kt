package id.idham.gitgud.feature.search

import id.idham.gitgud.core.common.UiState
import id.idham.gitgud.core.data.repository.UserRepository
import id.idham.gitgud.core.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @Mock
    private lateinit var repository: UserRepository
    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `SetQuery then SubmitSearch emits Success state`() = runTest {
        val fakeUsers = listOf(
            User(
                login = "idham",
                id = 1,
                avatarUrl = "https://example.com/avatar.png",
                htmlUrl = "https://example.com/user",
                name = "",
                bio = "",
                location = "",
                followers = 0,
                following = 0,
                publicRepos = 0
            )
        )
        whenever(repository.searchUsers("idham")).thenReturn(flowOf(fakeUsers))

        viewModel.sendAction(SearchAction.SetQuery("idham"))
        advanceTimeBy(500) // for debounce
        viewModel.sendAction(SearchAction.SubmitSearch)

        val state = viewModel.state.first { it.users is UiState.Success }
        val users = (state.users as UiState.Success).data

        assertEquals("idham", state.searchQuery)
        assertEquals(fakeUsers, users)
    }

    @Test
    fun `SubmitSearch emits Empty state when no result`() = runTest {
        whenever(repository.searchUsers("none")).thenReturn(flowOf(emptyList()))

        viewModel.sendAction(SearchAction.SetQuery("none"))
        advanceTimeBy(500)
        viewModel.sendAction(SearchAction.SubmitSearch)

        val state = viewModel.state.first { it.users !is UiState.Loading }
        assertTrue(state.users is UiState.Empty)
    }

    @Test
    fun `SubmitSearch emits Error state on failure`() = runTest {
        whenever(repository.searchUsers("error")).thenReturn(flow {
            throw IOException("Failed to load")
        })

        viewModel.sendAction(SearchAction.SetQuery("error"))
        advanceTimeBy(500)
        viewModel.sendAction(SearchAction.SubmitSearch)

        val state = viewModel.state.first { it.users !is UiState.Loading }
        assertTrue(state.users is UiState.Error)
        assertEquals("Failed to load", (state.users as UiState.Error).message)
    }

    @Test
    fun `Blank query loads initial users`() = runTest {
        val users = listOf(
            User(
                login = "mojombo",
                id = 1,
                avatarUrl = "https://example.com/avatar.png",
                htmlUrl = "https://example.com/user",
                name = "",
                bio = "",
                location = "",
                followers = 0,
                following = 0,
                publicRepos = 0
            )
        )
        whenever(repository.getUsers()).thenReturn(flowOf(users))

        viewModel.sendAction(SearchAction.SetQuery(""))
        advanceTimeBy(500)
        val state = viewModel.state.first { it.users is UiState.Success }
        assertEquals(users, (state.users as UiState.Success).data)
    }
}
