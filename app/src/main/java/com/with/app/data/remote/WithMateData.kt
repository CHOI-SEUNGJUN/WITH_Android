package com.with.app.data.remote

data class ResponseWithMateData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseWithMateArrayData>
)

data class ResponseWithMateArrayData(
    val name : String,
    val userImg : String
)