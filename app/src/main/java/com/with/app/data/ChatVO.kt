package com.with.app.data

data class ChatVO (
    val type : Int,
    val name : String?,
    val msg : String?,
    val userId : String?,
    val date : String?,
    val profile : String?
)

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
    return this.name == vo.name
}

fun ChatVO.isOtherName(vo: ChatVO): Boolean {
    return !this.isSameName(vo)
}