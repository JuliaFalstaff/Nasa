package com.example.nasaapp.model.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PODRetrofitImpl {

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }

    fun getRetrofitImp(): PictureOfTheDayApi {
        val podRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
        return podRetrofit.create(PictureOfTheDayApi::class.java)
    }
}