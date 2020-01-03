package com.with.app.data.remote

data class ResponseChatListData(
    val success : Boolean,
    val message : String?,
    val data : List<ResponseChatListArrayData>
)

data class ResponseChatListArrayData(
    val userIdx : Int,
    val boardIdx : Int,
    val roomId : String,
    val userImg : String,
    val name : String,
    val regionName : String,
    val title : String,
    val withDate : String?,
    val startDate : String,
    val endDate : String,
    val withFlag : Int,
    val evalFlag : Int,
    val writerImg : String,
    val regionImgE : String
)