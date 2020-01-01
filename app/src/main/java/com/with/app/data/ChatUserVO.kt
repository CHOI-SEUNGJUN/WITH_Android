package com.with.app.data

data class ChatUserVO (
    var boardIdx : Long = 0,
    var lastMessage : String? = null,
    var lastTime : String? = null,
    var unSeenCount : Long = -1
    )