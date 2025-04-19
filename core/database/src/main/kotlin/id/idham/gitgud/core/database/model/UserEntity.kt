package id.idham.gitgud.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.idham.gitgud.core.model.SimpleUser

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    @ColumnInfo(defaultValue = "")
    val name: String?
)

fun UserEntity.toDomain(): SimpleUser {
    return SimpleUser(
        login = login,
        id = id,
        nodeId = "",
        avatarUrl = avatarUrl,
        url = "",
        htmlUrl = htmlUrl
    )
}
