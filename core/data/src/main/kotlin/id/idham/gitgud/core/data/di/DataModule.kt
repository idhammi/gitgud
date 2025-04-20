package id.idham.gitgud.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.idham.gitgud.core.data.repository.UserRepository
import id.idham.gitgud.core.data.repository.UserRepositoryImpl
import id.idham.gitgud.core.data.util.ConnectivityManagerNetworkMonitor
import id.idham.gitgud.core.data.util.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    internal abstract fun provideNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}
