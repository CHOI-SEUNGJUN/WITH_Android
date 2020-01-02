package com.with.app.data.remote

data class RequestWithInviteData(
    val roomId : String,
    val withDate : String
)

data class ResponseWithInviteData(
    val success: Boolean,
    val message : String
)