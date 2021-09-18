package com.example.nasaapp.model.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.example.nasaapp.model.data.SputnikServerResponseData
import com.example.nasaapp.model.data.MarsPhotosServerResponseData
import com.example.nasaapp.model.data.PODServerResponseData
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }

    private val api by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
                .create(RetrofitApi::class.java)
    }

    fun getPictureOfTheDay(apiKey: String, date: String, podCallback: Callback<PODServerResponseData>) {
        api.getPictureOfTheDay(apiKey, date).enqueue(podCallback)
    }

    fun getEarthEpicPictureByDate(apiKey: String, earthEpicCallbackByDateNew: Callback<List<EarthEpicServerResponseData>>) {
        api.getEarthEpicImageByDate(apiKey).enqueue(earthEpicCallbackByDateNew)
    }

    fun getMarsPictureByDate(earth_date: String, apiKey: String, marsCallbackByDate: Callback<MarsPhotosServerResponseData>) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }

    fun getSputnikPictureByDate(lon: Float, lat: Float, date: String, dim: Float, apiKey: String, sputnikCallback: Callback<SputnikServerResponseData>) {
        api.getLandscapeImageFromSputnik(lon, lat, date, dim, apiKey).enqueue(sputnikCallback)
    }
}
