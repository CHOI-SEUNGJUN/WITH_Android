package com.with.app.data.local

data class ChatUserVO (
    var boardIdx : Int = 0,
    var lastMessage : String? = null,
    var lastTime : String? = null,
    var unSeenCount : Int = 0,
    var inviteFlag : Int = 0
    )

fun setLastMessage(vo: ChatUserVO, tempVO: ChatUserVO, value: String) {
    vo.lastMessage = value
    tempVO.lastMessage = value
}

fun setLastTime(vo: ChatUserVO, tempVO: ChatUserVO, value: String) {
    vo.lastTime = value
    tempVO.lastTime = value
}

fun setInviteFlag(vo: ChatUserVO, tempVO: ChatUserVO, value: Int) {
    vo.inviteFlag = value
    tempVO.inviteFlag = value
}

fun setBoardIdx(vo: ChatUserVO, tempVO: ChatUserVO, value: Int) {
    vo.boardIdx = value
    tempVO.boardIdx = value
}