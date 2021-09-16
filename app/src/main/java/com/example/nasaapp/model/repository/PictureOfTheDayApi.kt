package com.example.nasaapp.model.repository

import com.example.nasaapp.model.data.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
            @Query("api_key") apiKey: String,
    ): Call<PODServerResponseData>
}