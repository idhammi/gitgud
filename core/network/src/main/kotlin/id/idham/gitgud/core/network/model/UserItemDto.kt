package id.idham.gitgud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.idham.gitgud.core.model.SimpleUser

@JsonClass(generateAdapter = true)
data class UserItemDto(
    val login: String,
    val id: Int,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    val url: String,
    @Json(name = "html_url") val htmlUrl: String
)

fun UserItemDto.toDomain(): SimpleUser {
    return SimpleUser(
        login = login,
        id = id,
        nodeId = nodeId,
        avatarUrl = avatarUrl,
        url = url,
        htmlUrl = htmlUrl
    )
}
