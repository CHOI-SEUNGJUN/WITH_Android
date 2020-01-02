package com.with.app.data.remote

data class RequestChatOpenData(
    val userIdx1 : Int,
    val boardIdx : Int,
    val roomId : String
)

data class ResponseChatOpenData(
    val success : Boolean,
    val message : String
)