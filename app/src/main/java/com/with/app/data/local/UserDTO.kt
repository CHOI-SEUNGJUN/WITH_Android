package com.with.app.data.local

data class UserDTO(
    val userIdx : Int,
    val name : String,
    val userId : String,
    val age : Int,
    val gender : Boolean,
    val userImg : String,
    val hashTag : String,
    val intro : String,
    val like : Int,
    val active : Boolean
)