package com.with.app.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(context: Context, url : String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.load(context: Context, url : String, loading : ConstraintLayout, container : ConstraintLayout) {
    container.gone()
    loading.visible()
    Glide.with(context)
        .load(url)
        .into(this)
    loading.gone()
    container.visible()
}

fun ImageView.load(view: View, url : String) {
    Glide.with(view)
        .load(url)
        .into(this)
}

fun ImageView.load(context: Context, url : String, options: RequestOptions) {
    Glide.with(context)
        .load(url)
        .apply(options)
        .into(this)
}