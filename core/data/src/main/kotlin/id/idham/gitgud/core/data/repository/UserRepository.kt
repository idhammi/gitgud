package id.idham.gitgud.core.data.repository

import id.idham.gitgud.core.data.mapper.toEntity
import id.idham.gitgud.core.database.dao.UserDao
import id.idham.gitgud.core.database.model.UserEntity
import id.idham.gitgud.core.database.model.toDomain
import id.idham.gitgud.core.model.SimpleUser
import id.idham.gitgud.core.network.endpoint.GithubApiService
import id.idham.gitgud.core.network.model.UserItemDto
import id.idham.gitgud.core.network.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UserRepository {
    fun getUsers(): Flow<List<SimpleUser>>
    fun searchUsers(query: String): Flow<List<SimpleUser>>
}

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApiService,
    private val dao: UserDao
) : UserRepository {
    override fun getUsers(): Flow<List<SimpleUser>> = flow {
        val remote = api.getUsers()
        dao.clearAll()
        dao.insertAll(remote.map(UserItemDto::toEntity))
        emitAll(
            dao.getAll().map { it.map(UserEntity::toDomain) }
        )
    }

    override fun searchUsers(query: String): Flow<List<SimpleUser>> = flow {
        val result = api.searchUsers(query).items
        emit(result.map(UserItemDto::toDomain))
    }
}
