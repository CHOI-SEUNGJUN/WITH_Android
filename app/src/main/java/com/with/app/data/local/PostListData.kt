package com.with.app.data.local

data class PostListData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<PostItem>
)

data class PostItem(
    //val profile: String,
    val region: String,
    val date: String,
    val time: String,
    val title: String,
    val participant: String
)