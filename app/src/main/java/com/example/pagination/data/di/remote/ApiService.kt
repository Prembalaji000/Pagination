package com.example.pagination.data.di.remote

import com.example.pagination.data.di.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    //https://pixabay.com/api/?key=48185039-a4897d229949cadf17c51387d&q=yellow+flowers&image_type=photo

    @GET("api/")
    suspend fun getImages(
        @Query("key") apiKey : String = "48185039-a4897d229949cadf17c51387d",
        @Query("q") q : String,
        @Query("page") page : Int
    ) : ImageResponse
}