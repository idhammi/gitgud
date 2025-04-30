package id.idham.gitgud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.idham.gitgud.core.model.data.UserRepo

@JsonClass(generateAdapter = true)
data class UserRepoDto(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    @Json(name = "stargazers_count") val stargazersCount: Int,
    @Json(name = "updated_at") val updatedAt: String
)

fun UserRepoDto.toDomain(): UserRepo {
    return UserRepo(
        id = id,
        name = name,
        description = description,
        language = language,
        stargazersCount = stargazersCount,
        updatedAt = updatedAt
    )
}
