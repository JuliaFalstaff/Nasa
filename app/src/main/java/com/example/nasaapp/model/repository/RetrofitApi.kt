package com.example.nasaapp.model.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.example.nasaapp.model.data.SputnikServerResponseData
import com.example.nasaapp.model.data.MarsPhotosServerResponseData
import com.example.nasaapp.model.data.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
            @Query("api_key") apiKey: String,
    ): Call<PODServerResponseData>

    @GET("EPIC/api/natural")
    fun getEarthEpicImageByDate(
            @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
            @Query("earth_date") earth_date: String,
            @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>

    @GET("/planetary/earth/assets")
    fun getLandscapeImageFromSputnik(
            @Query("lon") lon: Float,
            @Query("lat") lat: Float,
            @Query("date") dateString: String,
            @Query("dim") dim: Float,
            @Query("api_key") apiKey: String
    ): Call<SputnikServerResponseData>
}

