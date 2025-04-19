package id.idham.gitgud.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.idham.gitgud.core.database.dao.UserDao
import id.idham.gitgud.core.database.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
