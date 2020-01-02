package com.with.app.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(context: Context, url : String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.load(view: View, url : String) {
    Glide.with(view)
        .load(url)
        .into(this)
}