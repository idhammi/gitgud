package id.idham.gitgud.core.data.repository

import id.idham.gitgud.core.data.mapper.toEntity
import id.idham.gitgud.core.data.utils.NetworkMonitor
import id.idham.gitgud.core.database.dao.UserDao
import id.idham.gitgud.core.database.model.UserEntity
import id.idham.gitgud.core.database.model.toDomain
import id.idham.gitgud.core.model.ErrorType
import id.idham.gitgud.core.model.Result
import id.idham.gitgud.core.model.data.User
import id.idham.gitgud.core.model.data.UserRepo
import id.idham.gitgud.core.network.endpoint.GithubApiService
import id.idham.gitgud.core.network.model.UserItemDto
import id.idham.gitgud.core.network.model.UserRepoDto
import id.idham.gitgud.core.network.model.toDomain
import id.idham.gitgud.core.network.utils.ResultWrapper
import id.idham.gitgud.core.network.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UserRepository {
    fun getUsers(): Flow<Result<List<User>>>
    fun searchUsers(query: String): Flow<Result<List<User>>>
    fun getUser(username: String): Flow<Result<User>>
    fun getUserRepo(username: String): Flow<Result<List<UserRepo>>>
}

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApiService,
    private val dao: UserDao,
    private val networkMonitor: NetworkMonitor,
) : UserRepository {

    // offline supported
    override fun getUsers(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        if (networkMonitor.isOnline.first()) {
            when (val response = safeApiCall { api.getUsers() }) {
                is ResultWrapper.Success -> {
                    dao.clearAll()
                    dao.insertAll(response.data.map(UserItemDto::toEntity))
                }

                is ResultWrapper.Error -> {
                    emit(Result.Error(response.error))
                }
            }
        }
        dao.getAll()
            .map { it.map(UserEntity::toDomain) }
            .collect { users ->
                emit(Result.Success(users))
            }
    }

    // network only
    override fun searchUsers(query: String): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        if (!networkMonitor.isOnline.first()) {
            emit(Result.Error(ErrorType.NetworkError))
            return@flow
        }

        when (val response = safeApiCall { api.searchUsers(query).items }) {
            is ResultWrapper.Success -> {
                emit(Result.Success(response.data.map(UserItemDto::toDomain)))
            }

            is ResultWrapper.Error -> {
                emit(Result.Error(response.error))
            }
        }
    }

    // offline supported
    override fun getUser(username: String): Flow<Result<User>> = flow {
        emit(Result.Loading)

        val localUser = dao.getUser(username).firstOrNull()
        if (localUser != null && localUser.name.isNotBlank()) {
            emit(Result.Success(localUser.toDomain()))
        }

        if (networkMonitor.isOnline.first()) {
            when (val response = safeApiCall { api.getUserDetail(username) }) {
                is ResultWrapper.Success -> {
                    dao.insert(response.data.toEntity())
                    emit(Result.Success(response.data.toEntity().toDomain()))
                }

                is ResultWrapper.Error -> {
                    if (localUser == null) emit(Result.Error(response.error))
                }
            }
        } else {
            if (localUser == null) emit(Result.Error(ErrorType.NetworkError))
        }
    }

    // network only
    override fun getUserRepo(username: String): Flow<Result<List<UserRepo>>> = flow {
        emit(Result.Loading)
        if (!networkMonitor.isOnline.first()) {
            emit(Result.Error(ErrorType.NetworkError))
            return@flow
        }

        when (val response = safeApiCall { api.getUserRepos(username) }) {
            is ResultWrapper.Success -> {
                emit(Result.Success(response.data.map(UserRepoDto::toDomain)))
            }

            is ResultWrapper.Error -> {
                emit(Result.Error(response.error))
            }
        }
    }
}
