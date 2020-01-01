package com.with.app.data.remote

data class RequestBoardData(
    val regionCode : String,
    val title : String,
    val content : String,
    val startDate : String,
    val endDate : String,
    val filter : Int
)

data class ResponseBoardData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseBoardObjectData>
)

data class ResponseBoardEditData(
    val success: Boolean,
    val message: String
)

data class ResponseBoardObjectData(
    val boardIdx : Int,
    val regionCode : String,
    val regionName : String,
    val title : String,
    val content : String,
    val uploadTime : String,
    val startDate : String,
    val endDate : String,
    val withNum : Int,
    val filter : Int,
    val userIdx : Int,
    val name : String,
    val birth : Int,
    val gender : Int,
    val userImg : String,
    val intro : String
)