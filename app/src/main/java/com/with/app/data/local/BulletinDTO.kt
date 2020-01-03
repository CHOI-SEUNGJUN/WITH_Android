package com.with.app.data.local

data class BulletinDTO(
    val bltIdx : Int,
    val country : String,
    val region : String,
    val title : String,
    val content : String,
    val startDate : String,
    val endDate : String,
    val userIdx : Int,
    val active : Boolean,
    val with : Int
)