package com.with.app.data.remote

data class ResponseLatelyBoardData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseLatelyBoardArrayData>
)

data class ResponseLatelyBoardArrayData(
    val name : String,
    val userImg : String,
    val regionCode : String
    // val title : String
)