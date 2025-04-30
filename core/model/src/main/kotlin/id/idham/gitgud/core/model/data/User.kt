package id.idham.gitgud.core.model.data

data class User(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val htmlUrl: String,

    // Detail fields
    val name: String = "",
    val bio: String = "",
    val location: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0
)
