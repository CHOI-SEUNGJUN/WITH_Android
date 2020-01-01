package com.with.app.data.remote

data class RequestMyPageData (
    val name : String,
    val birth : Int,
    val gender : Int,
    val userImg : String,
    val intro : String?,
    val likeNum : Int,
    val dislikeNum : Int,
    val badge : Int
)
