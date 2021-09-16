package com.example.nasaapp.model.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.example.nasaapp.model.data.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
            @Query("api_key") apiKey: String,
    ): Call<PODServerResponseData>

    @GET("EPIC/api/natural/images")
    fun getEarthEpicImage(
            @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET("EPIC/api/natural/date/{date}")
    fun getEarthEpicImageByDate(
            @Query("date") date: String,
            @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET("EPIC/api/natural/date/")
    fun getEarthEpicImageByDateNew(
            @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>
}