package com.with.app.extension

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.WindowManager
import com.airbnb.lottie.LottieAnimationView
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

fun returnNowDate() : String {
    val now = Calendar.getInstance().time
    val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
    return pattern.format(now)
}

fun isDiffDays(now : String?, next : String?) : Boolean {
    val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
    val nowDate = pattern.parse(now)
    val nextDate = pattern.parse(next)
    val diffs = (nextDate.time - nowDate.time) / (60 * 1000)
    return diffs.toString() == "0"
}

fun Activity.showLoading(ani : LottieAnimationView) {
    ani.playAnimation()
    ani.visible()
    ani.loop(true)
    window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.hideLoading(ani : LottieAnimationView) {
    ani.pauseAnimation()
    ani.gone()
    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}