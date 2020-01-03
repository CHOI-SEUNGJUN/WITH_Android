package com.with.app.data.local

data class ChatVO (
    var type : Int = 0,
    var msg : String? = null,
    var userIdx : Int = 0,
    var date : String? = null
)

fun ChatVO.isMessage(): Boolean {
    return this.type == 0 || this.type == 1
}

fun ChatVO.isNotMessage(): Boolean {
    return !this.isMessage()
}

fun ChatVO.isNotMessageButChat(): Boolean {
    return this.isNotMessage() && this.isChat()
}

fun ChatVO.isNotChat(): Boolean {
    return this.type == 5 || this.type == 6
}

fun ChatVO.isChat(): Boolean {
    return !this.isNotChat()
}

fun ChatVO.isOtherChat(): Boolean {
    return this.isChat() && (this.type == 1 || this.type == 3 || this.type == 4)
}

fun ChatVO.isSameName(vo: ChatVO): Boolean {
    return this.userIdx == vo.userIdx
}

fun ChatVO.isOtherName(vo: ChatVO): Boolean {
    return !this.isSameName(vo)
}

fun ChatVO.isSameName(idx : Int): Boolean {
    return this.userIdx == idx
}

fun ChatVO.isOtherName(idx : Int): Boolean {
    return !this.isSameName(idx)
}

fun ChatVO.isInviteApply(): Boolean {
    return this.isNotMessageButChat() && this.msg?.contains("동행 신청 메시지입니다.") ?: false
}

fun ChatVO.isInviteComplete(): Boolean {
    return this.isNotMessageButChat() && this.msg?.contains("동행 성사 메시지입니다.") ?: false
}