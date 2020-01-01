package com.with.app.data.remote

data class ResponseDetailBoardData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseBoardObjectData>
)