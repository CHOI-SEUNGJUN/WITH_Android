package com.with.app.util

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.*

fun String.parseDate(): Date {
    val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
    return pattern.parse(this)
}

fun Date.isDiffDay(other: Date): Boolean {
    return (this.time - other.time) / (1000 * 60 * 60 * 24) != 0L
}

fun DatabaseReference.addListener(
    onCancelled : (DatabaseError) -> Unit = {},
    onChildMoved : (DataSnapshot, String?) -> Unit = { _, _ -> Unit },
    onChildChanged : (DataSnapshot, String?) -> Unit = { _, _ -> Unit },
    onChildAdded : (DataSnapshot, String?) -> Unit = { _, _ -> Unit },
    onChildRemoved: (DataSnapshot) -> Unit = {}
) {
    this.addChildEventListener(object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) { onCancelled(p0) }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) { onChildMoved(p0, p1) }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) { onChildChanged(p0, p1) }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) { onChildAdded(p0, p1) }

        override fun onChildRemoved(p0: DataSnapshot) { onChildRemoved(p0) }
    })
}