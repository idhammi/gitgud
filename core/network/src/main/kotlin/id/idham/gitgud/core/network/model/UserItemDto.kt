package id.idham.gitgud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.idham.gitgud.core.model.data.User

@JsonClass(generateAdapter = true)
data class UserItemDto(
    val login: String,
    val id: Int,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val htmlUrl: String
)

fun UserItemDto.toDomain(): User {
    return User(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )
}
