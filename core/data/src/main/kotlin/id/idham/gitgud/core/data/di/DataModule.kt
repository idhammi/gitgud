package id.idham.gitgud.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.idham.gitgud.core.data.repository.UserRepository
import id.idham.gitgud.core.data.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}
