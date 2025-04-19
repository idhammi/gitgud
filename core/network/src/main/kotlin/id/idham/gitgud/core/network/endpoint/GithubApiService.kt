package id.idham.gitgud.core.network.endpoint

import id.idham.gitgud.core.network.model.SearchUserResponseDto
import id.idham.gitgud.core.network.model.UserDetailDto
import id.idham.gitgud.core.network.model.UserItemDto
import id.idham.gitgud.core.network.model.UserRepoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int? = null,
        @Query("per_page") perPage: Int = 20
    ): List<UserItemDto>

    @GET("/users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String,
    ): UserDetailDto

    @GET("/users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UserRepoDto>

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 20
    ): SearchUserResponseDto
}
