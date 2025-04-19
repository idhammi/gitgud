package id.idham.gitgud.core.model

data class SimpleUser(
    val login: String,
    val id: Int,
    val nodeId: String,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String
)
