package id.idham.gitgud.core.data.repository

import id.idham.gitgud.core.data.mapper.toEntity
import id.idham.gitgud.core.data.util.NetworkMonitor
import id.idham.gitgud.core.database.dao.UserDao
import id.idham.gitgud.core.database.model.UserEntity
import id.idham.gitgud.core.database.model.toDomain
import id.idham.gitgud.core.model.User
import id.idham.gitgud.core.model.UserRepo
import id.idham.gitgud.core.network.endpoint.GithubApiService
import id.idham.gitgud.core.network.model.UserItemDto
import id.idham.gitgud.core.network.model.UserRepoDto
import id.idham.gitgud.core.network.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun searchUsers(query: String): Flow<List<User>>
    fun getUser(username: String): Flow<User?>
    fun getUserRepo(username: String): Flow<List<UserRepo>>
}

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApiService,
    private val dao: UserDao,
    private val networkMonitor: NetworkMonitor,
) : UserRepository {

    override fun getUsers(): Flow<List<User>> = flow {
        emitAll(
            dao.getAll().map { it.map(UserEntity::toDomain) }
        )
    }.onStart {
        if (networkMonitor.isOnline.first()) {
            val remote = api.getUsers()
            dao.clearAll()
            dao.insertAll(remote.map(UserItemDto::toEntity))
        }
    }

    override fun searchUsers(query: String): Flow<List<User>> = flow {
        if (!networkMonitor.isOnline.first()) {
            emit(emptyList())
            return@flow
        }

        val result = api.searchUsers(query).items
        emit(result.map(UserItemDto::toDomain))
    }

    override fun getUser(username: String): Flow<User?> = flow {
        emitAll(
            dao.getUser(username).filterNotNull().map(UserEntity::toDomain)
        )
    }.onStart {
        if (networkMonitor.isOnline.first()) {
            val remote = api.getUserDetail(username)
            dao.insert(remote.toEntity())
        }
    }

    override fun getUserRepo(username: String): Flow<List<UserRepo>> = flow {
        if (!networkMonitor.isOnline.first()) {
            emit(emptyList())
            return@flow
        }

        val result = api.getUserRepos(username)
        emit(result.map(UserRepoDto::toDomain))
    }
}
