package fnr.bedir.echo.api

import fnr.bedir.echo.model.MockDataResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 *CREATED BY bbedir on 2019-09-06.
 */
interface ApiService {
    @GET("mock-api/db")
    fun getMockData(): Call<MockDataResponse>
}