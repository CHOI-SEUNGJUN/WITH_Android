package com.with.app.util

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter
import java.text.SimpleDateFormat
import java.util.*

fun String.parseDate(): Date {
    val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
    return pattern.parse(this)
}

fun Date.isDiffDay(other: Date): Boolean {
    return (this.time - other.time) / (1000 * 60 * 60 * 24) != 0L
}

fun String.toSpanned() : Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ImageView.change(id : Int) {
    this.setImageResource(id)
}

fun RecyclerView.adjustScroll() {
    this.scrollToPosition(this.adapter!!.itemCount - 1)
}

fun RecyclerView.setLinearLayoutManager(context : Context) {
    val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.layoutManager = lm
}