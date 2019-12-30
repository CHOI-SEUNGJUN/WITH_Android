package com.with.app.data.remote

data class ResponseSignUpData(
    val success : Boolean,
    val message : String
)


// RequestSignUpData는 구현하지 않음.(Multipart)