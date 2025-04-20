package id.idham.gitgud.core.data

import id.idham.gitgud.core.network.endpoint.GithubApiService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

class GithubApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: GithubApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        apiService = retrofit.create(GithubApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUsers returns expected user list`() = runTest {
        val responseBody = File("src/test/resources/users_response.json").readText()
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        val result = apiService.getUsers()

        assertEquals("mojombo", result.first().login)
        assertEquals(5, result.size)

        val request = mockWebServer.takeRequest()
        assertEquals("/users?per_page=20", request.path)
    }
}
