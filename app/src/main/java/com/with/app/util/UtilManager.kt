package com.with.app.util

import java.text.SimpleDateFormat
import java.util.*

fun String.parseDate(): Date {
    val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
    return pattern.parse(this)
}

fun Date.isDiffDay(other: Date): Boolean {
    return (this.time - other.time) / (1000 * 60 * 60 * 24) != 0L
}
