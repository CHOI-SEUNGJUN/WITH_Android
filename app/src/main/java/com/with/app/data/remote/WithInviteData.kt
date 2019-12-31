package com.with.app.data.remote

data class RequestWithInviteData(
    val receiverIdx : Int,
    val boardIdx : Int,
    val withDate : String
)

data class ResponseWithInviteData(
    val success: Boolean,
    val message : String
)