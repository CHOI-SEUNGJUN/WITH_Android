package com.with.app.data

data class ChatVO (
    val type : Int,
    val name : String,
    val msg : String?,
    val userid : String,
    val date : String,
    val profile : String?
)