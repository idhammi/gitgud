package id.idham.gitgud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetailDto(
    val login: String,
    val id: Int,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val htmlUrl: String,
    val name: String?,
    val bio: String?,
    val location: String?,
    val followers: Int?,
    val following: Int?,
    @Json(name = "public_repos") val publicRepos: Int?
)
