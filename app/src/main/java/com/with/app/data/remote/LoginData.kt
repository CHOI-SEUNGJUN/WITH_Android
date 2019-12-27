package com.with.app.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseLoginData(
    val success : Boolean,
    val message : String,
    @SerializedName("data")
    val token : String
)

data class RequestLoginData(
    val userId : String,
    val password : String
)