package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO

class ChatMyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val msg : TextView = view.findViewById(R.id.tv_content)

    fun bind(data : ChatVO) {
        msg.text = data.msg
    }
}