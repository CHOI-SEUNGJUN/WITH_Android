package com.with.app.data

data class ChatListVO(
    val chatRoomId: String?,
    val profile: String,
    val name: String,
    val title: String,
    val circle: String,
    var response: ChatUserVO
)