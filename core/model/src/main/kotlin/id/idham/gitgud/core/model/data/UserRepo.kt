package id.idham.gitgud.core.model.data

data class UserRepo(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    val stargazersCount: Int,
    val updatedAt: String
)
