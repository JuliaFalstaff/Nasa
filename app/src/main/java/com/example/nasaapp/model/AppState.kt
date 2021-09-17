package com.example.nasaapp.model

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.example.nasaapp.model.data.SputnikServerResponseData
import com.example.nasaapp.model.data.MarsPhotosServerResponseData
import com.example.nasaapp.model.data.PODServerResponseData

sealed class AppState {
    data class Success(val serverResponseData: PODServerResponseData) : AppState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : AppState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class SuccessSputnik(val serverResponseData: SputnikServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
