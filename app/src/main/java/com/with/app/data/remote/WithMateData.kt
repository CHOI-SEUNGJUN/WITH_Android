package com.with.app.data.remote

data class ResponseWithMateData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseChatListArrayData>
)