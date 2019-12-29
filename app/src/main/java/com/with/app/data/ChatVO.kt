package com.with.app.data

data class ChatVO (
    var type : Int = 0,
    var name : String? = null,
    var msg : String? = null,
    var userId : String? = null,
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
    return this.userId == vo.userId
}

fun ChatVO.isOtherName(vo: ChatVO): Boolean {
    return !this.isSameName(vo)
}

fun ChatVO.isSameName(id : String): Boolean {
    return this.userId == id
}

fun ChatVO.isOtherName(id : String): Boolean {
    return !this.isSameName(id)
}

fun ChatVO.isInviteApply(): Boolean {
    return this.isNotMessageButChat() && this.msg == "신청"
}

fun ChatVO.isInviteComplete(): Boolean {
    return this.isNotMessageButChat() && this.msg == "완료"
}