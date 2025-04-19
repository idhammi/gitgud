package id.idham.gitgud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUserResponseDto(
    @Json(name = "total_count") val totalCount: Int,
    val items: List<UserItemDto>
)
