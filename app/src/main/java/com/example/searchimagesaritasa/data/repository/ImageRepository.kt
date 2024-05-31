package com.example.searchimagesaritasa.data.repository

import com.example.searchimagesaritasa.data.api.ApiClient
import com.example.searchimagesaritasa.data.model.ImageResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository {
    private val apiService = ApiClient.apiService

    fun searchImages(query: String, onResult: (ImageResult?) -> Unit) {
        val body = ApiClient.createRequestBody(query)
        apiService.searchImages(body).enqueue(object : Callback<ImageResult> {
            override fun onResponse(call: Call<ImageResult>, response: Response<ImageResult>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<ImageResult>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
