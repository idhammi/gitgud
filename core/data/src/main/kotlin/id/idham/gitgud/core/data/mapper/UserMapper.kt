package id.idham.gitgud.core.data.mapper

import id.idham.gitgud.core.database.model.UserEntity
import id.idham.gitgud.core.network.model.UserItemDto

fun UserItemDto.toEntity() = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    name = ""
)
