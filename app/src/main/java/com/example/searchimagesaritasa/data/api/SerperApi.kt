package com.example.searchimagesaritasa.data.api

import com.example.searchimagesaritasa.data.model.ImageResult
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SerperApi {
    @POST("images")
    fun searchImages(@Body requestBody: RequestBody): Call<ImageResult>
}