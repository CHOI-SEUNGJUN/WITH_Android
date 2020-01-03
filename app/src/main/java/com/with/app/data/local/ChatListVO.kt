package com.with.app.data.local

import com.with.app.data.local.ChatUserVO
import com.with.app.data.remote.ResponseChatListArrayData

data class ChatListVO(
    var ourServer: ResponseChatListArrayData,
    var fireBase: ChatUserVO
)