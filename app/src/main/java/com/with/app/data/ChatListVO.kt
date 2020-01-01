package com.with.app.data

import com.with.app.data.remote.ResponseChatListArrayData

data class ChatListVO(
    var ourServer: ResponseChatListArrayData,
    var fireBase: ChatUserVO
)