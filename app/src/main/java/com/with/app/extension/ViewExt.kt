package com.with.app.extension

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun RecyclerView.adjustScroll() {
    this.scrollToPosition(this.adapter!!.itemCount - 1)
}

fun RecyclerView.setLinearLayoutManager(context : Context) {
    val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.layoutManager = lm
}