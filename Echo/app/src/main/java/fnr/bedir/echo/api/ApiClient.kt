package fnr.bedir.echo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *CREATED BY bbedir on 2019-09-06.
 */
class ApiClient {

    companion object {
        val mBaseUrl: String = "https://my-json-server.typicode.com/emredirican/"
        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build()


        }
    }
}