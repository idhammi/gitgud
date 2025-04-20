package id.idham.gitgud.core.data.mapper

import id.idham.gitgud.core.database.model.UserEntity
import id.idham.gitgud.core.network.model.UserDetailDto
import id.idham.gitgud.core.network.model.UserItemDto

fun UserItemDto.toEntity() = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    name = "",
    bio = "",
    location = "",
)

fun UserDetailDto.toEntity() = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    name = name.orEmpty(),
    bio = bio.orEmpty(),
    location = location.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0,
    publicRepos = publicRepos ?: 0,
)
