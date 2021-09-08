package com.example.nasaapp.model

import com.example.nasaapp.model.data.PODServerResponseData

sealed class PODData {
    data class Success(val serverResponseData: PODServerResponseData) : PODData()
    data class Error(val error: Throwable) : PODData()
    object Loading : PODData()
}
