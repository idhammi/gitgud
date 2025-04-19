package id.idham.gitgud.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.idham.gitgud.core.database.AppDatabase
import id.idham.gitgud.core.database.dao.UserDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesUserDao(
        database: AppDatabase,
    ): UserDao = database.userDao()
}
