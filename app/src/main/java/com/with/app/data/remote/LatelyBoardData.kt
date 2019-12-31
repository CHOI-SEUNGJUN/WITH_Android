package com.with.app.data.remote

data class ResponseLatelyBoardData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseLatelyBoardArrayData>
)

data class ResponseLatelyBoardArrayData(
    val boardIdx : Int,
    val name : String,
    val userImg : String,
    val regionName : String,
    val title : String
)