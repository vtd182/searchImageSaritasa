package com.example.searchimagesaritasa.data.repository

import android.util.Log
import com.example.searchimagesaritasa.data.api.ApiClient
import com.example.searchimagesaritasa.data.model.ImageResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository {
    private val apiService = ApiClient.apiService

    fun searchImages(query: String, page: Int, onResult: (ImageResult?) -> Unit) {
        Log.e("ImageRepository", "searchImages: $query")
        val body = ApiClient.createRequestBody(query, page)
        apiService.searchImages(body).enqueue(object : Callback<ImageResult> {
            override fun onResponse(call: Call<ImageResult>, response: Response<ImageResult>) {
                Log.e("ImageRepository", "onResponse: " + response.body())
                onResult(response.body())
            }

            override fun onFailure(call: Call<ImageResult>, t: Throwable) {
                Log.e("ImageRepository", "onFailure: " + t.message)
                onResult(null)
            }
        })
    }
}
