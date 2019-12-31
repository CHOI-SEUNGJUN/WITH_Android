package com.with.app.data.remote

data class ResponseHomeBgData(
    val success : Boolean,
    val message : String,
    val data : ResponseHomeBgObjectData
)

data class ResponseHomeBgObjectData(
    val regionImgH : String
)