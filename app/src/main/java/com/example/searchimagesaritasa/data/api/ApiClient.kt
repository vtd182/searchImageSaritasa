package com.example.searchimagesaritasa.data.api
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://google.serper.dev/"
    private const val API_KEY = "1a8bf15f2d0f96330173cdfb4bda0fe0cd6c4526"

    private val mediaType = "application/json".toMediaType()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("X-API-KEY", API_KEY)
                .header("Content-Type", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: SerperApi = retrofit.create(SerperApi::class.java)

    fun createRequestBody(query: String): RequestBody {
        val json = """{"q":"$query"}"""
        return json.toRequestBody(mediaType)
    }
}
