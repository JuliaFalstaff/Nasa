package com.example.nasaapp.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.BuildConfig
import com.example.nasaapp.R
import com.example.nasaapp.model.PODData
import com.example.nasaapp.model.data.PODServerResponseData
import com.example.nasaapp.model.repository.PODRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel(private val liveDataToObserve: MutableLiveData<PODData> = MutableLiveData(),
                   private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PODData> {
        return liveDataToObserve
    }

    fun getPODFromServer() {
        liveDataToObserve.postValue(PODData.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODData.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getRetrofitImp().getPictureOfTheDay(apiKey).enqueue(
                object : Callback<PODServerResponseData> {

                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataToObserve.postValue(PODData.Success(response.body()!!))
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveDataToObserve.postValue(PODData.Error(Throwable(UNKNOWN_ERROR)))
                            } else {
                                liveDataToObserve.postValue(PODData.Error(Throwable(message)))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveDataToObserve.postValue(PODData.Error(t))
                    }
                }
            )
        }
    }


    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }
}