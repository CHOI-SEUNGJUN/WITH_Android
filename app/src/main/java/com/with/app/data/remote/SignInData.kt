package com.with.app.data.remote

data class ResponseSignInData(
    val success : Boolean,
    val message : String,
    val data : ResponseAuthData
)

data class RequestSignInData(
    val userId : String,
    val password : String
)

data class ResponseAuthData(
    val token : String,
    val userIdx : Int,
    val name : String
)