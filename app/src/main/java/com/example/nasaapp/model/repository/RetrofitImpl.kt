package com.example.nasaapp.model.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
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

    fun getPictureOfTheDay(apiKey: String, podCallback: Callback<PODServerResponseData>) {
        api.getPictureOfTheDay(apiKey).enqueue(podCallback)
    }

    fun getEarthEpicPicture(apiKey: String, earthEpicCallback: Callback<List<EarthEpicServerResponseData>>) {
        api.getEarthEpicImage(apiKey).enqueue(earthEpicCallback)
    }



}