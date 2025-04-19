package id.idham.gitgud.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.idham.gitgud.core.network.endpoint.GithubApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GithubNetworkModule {

    private const val GITHUB_BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideGithubApiService(
        moshiConverter: MoshiConverterFactory
    ): GithubApiService {
        return Retrofit
            .Builder()
            .baseUrl(GITHUB_BASE_URL)
            .addConverterFactory(moshiConverter)
            .build()
            .create(GithubApiService::class.java)
    }
}
