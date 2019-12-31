package com.with.app.data.remote

data class ResponseSearchBoardData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseSearchBoardArrayData>
)

data class ResponseSearchBoardArrayData(
    val boardIdx : Int,
    val regionCode : String,
    val regionName : String,
    val title : String,
    val uploadTime : String,
    val startDate : String,
    val endDate : String,
    val withNum : Int,
    val filter : Int,
    val userImg : String
)