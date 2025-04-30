package id.idham.gitgud.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.idham.gitgud.core.model.data.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    @ColumnInfo(defaultValue = "") val name: String,
    @ColumnInfo(defaultValue = "") val bio: String,
    @ColumnInfo(defaultValue = "") val location: String,
    val followers: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0,
)

fun UserEntity.toDomain(): User {
    return User(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        name = name,
        bio = bio,
        location = location,
        followers = followers,
        following = following,
        publicRepos = publicRepos
    )
}
