package com.with.app.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseSignInData(
    val success : Boolean,
    val message : String,
    @SerializedName("data")
    val token : String
)

data class RequestSignInData(
    val userId : String,
    val password : String
)