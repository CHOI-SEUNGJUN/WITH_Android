package com.with.app.data.remote

data class ResponseMyPageData(
    val success : Boolean,
    val message : String,
    val data : ResponseObjectMyPageData
)

data class ResponseObjectMyPageData (
    val name : String,
    val birth : Int,
    val gender : Int,
    val userImg : String,
    val intro : String?,
    val badge : Int,
    val userBgImg : String
)

data class ResponsePutMyPageData (
    val success : Boolean,
    val message : String
)
