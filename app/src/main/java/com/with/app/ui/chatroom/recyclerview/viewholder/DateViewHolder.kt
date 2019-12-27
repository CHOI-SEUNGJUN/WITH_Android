package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO

class DateViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val date : TextView = view.findViewById(R.id.tv_date)

    fun bind(data : ChatVO) {
        date.text = data.date
    }
}