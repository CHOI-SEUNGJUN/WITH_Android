package com.with.app.ui.home

import android.content.Context

class Banner (
    var image: String
) {
    fun getImageId(context: Context): Int {
        return context.resources.getIdentifier(image, "drawable", context.packageName)
    }
}