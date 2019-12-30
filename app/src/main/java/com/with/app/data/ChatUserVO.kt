package com.with.app.data

data class ChatUserVO (
    var boardIdx : Int = 0,
    var lastMessage : String? = null,
    var lastTime : String? = null,
    var unSeenCount : Int = -1
    )