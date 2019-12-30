package com.with.app.data.remote

import okhttp3.MultipartBody

data class ResponseSignUpData(
    val success : Boolean,
    val message : String
)

data class RequestSignUpData(
    val userId : String,
    val password : String,
    val name : String,
    val birth : String,
    val gender : Int,
    val userImg : MultipartBody.Part?
)