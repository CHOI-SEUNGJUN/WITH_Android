package com.with.app.data.remote

data class ResponseChatListData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseChatListArrayData>
)

data class ResponseChatListArrayData(
    val userImg : String,
    val name : String,
    val regionName : String,
    val title : String,
    val content : String,
    val withDate : String,
    val startDate : String,
    val endDate : String,
    val withFlag : Int
)