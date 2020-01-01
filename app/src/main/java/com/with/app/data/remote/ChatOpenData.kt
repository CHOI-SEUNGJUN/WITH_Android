package com.with.app.data.remote

data class RequestChatOpenData(
    val receiverIdx : Int,
    val boardIdx : Int,
    val roomId : String
)

data class ResponseChatOpenData(
    val success : Boolean,
    val message : String
)