package id.idham.gitgud.core.data

import id.idham.gitgud.core.data.repository.UserRepository
import id.idham.gitgud.core.data.repository.UserRepositoryImpl
import id.idham.gitgud.core.data.utils.NetworkMonitor
import id.idham.gitgud.core.database.dao.UserDao
import id.idham.gitgud.core.database.model.UserEntity
import id.idham.gitgud.core.database.model.toDomain
import id.idham.gitgud.core.network.endpoint.GithubApiService
import id.idham.gitgud.core.network.model.UserItemDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @Mock
    lateinit var api: GithubApiService

    @Mock
    lateinit var dao: UserDao

    @Mock
    lateinit var networkMonitor: NetworkMonitor

    private lateinit var repository: UserRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = UserRepositoryImpl(api, dao, networkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUsers emits data from room after api sync`() = runTest {
        // given
        val remoteUsers = listOf(
            UserItemDto(
                login = "idham",
                id = 1,
                avatarUrl = "https://example.com/avatar.png",
                htmlUrl = "https://example.com/user",
            )
        )
        val entityList = listOf(
            UserEntity(
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
        val flowFromDao = flowOf(entityList)

        whenever(networkMonitor.isOnline).thenReturn(flowOf(true))
        whenever(api.getUsers()).thenReturn(remoteUsers)
        whenever(dao.getAll()).thenReturn(flowFromDao)

        // when
        val result = repository.getUsers().first()

        // then
        verify(api).getUsers()
        verify(dao).insertAll(any())
        assertEquals(entityList.map(UserEntity::toDomain), result)
    }

    @Test
    fun `getUser emits from DB when offline`() = runTest {
        // given
        val userEntity = UserEntity(
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

        whenever(networkMonitor.isOnline).thenReturn(flowOf(false))
        whenever(dao.getUser("idham")).thenReturn(flowOf(userEntity))

        // when
        val result = repository.getUser("idham").first()

        // then
        assertEquals("idham", result?.login)
        verify(api, never()).getUserDetail(any())
    }
}
