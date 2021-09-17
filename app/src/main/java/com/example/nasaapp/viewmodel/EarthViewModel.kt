package com.example.nasaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.BuildConfig
import com.example.nasaapp.model.AppState
import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.example.nasaapp.model.repository.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getEarthEpicPictureFromServerByDate() {
        liveDataToObserve.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getEarthEpicPictureByDate(apiKey, earthEpicCallbackByDate)
        }
    }

    val earthEpicCallbackByDate = object : Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
                call: Call<List<EarthEpicServerResponseData>>,
                response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.SuccessEarthEpic(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }
}