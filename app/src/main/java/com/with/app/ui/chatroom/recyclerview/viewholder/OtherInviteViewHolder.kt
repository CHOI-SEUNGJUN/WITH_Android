package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO
import java.text.SimpleDateFormat

class OtherInviteViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img : ImageView = view.findViewById(R.id.iv_tripimage)
    val msg : TextView = view.findViewById(R.id.tv_message)
    val date : TextView = view.findViewById(R.id.tv_date)
    val accept : Button = view.findViewById(R.id.btn_accept)

    fun bind(data : ChatVO, next : ChatVO, last : Boolean) {
        msg.text = data.msg
        date.text = data.date?.substring(13)

        if (!last) {
            if ((next.type == 1 || next.type == 3 || next.type == 4)) {
                val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
                val now_date = pattern.parse(data.date)
                val next_date = pattern.parse(next.date)
                val diffs = (next_date.time - now_date.time) / (60 * 1000) // minutes 단위

                if (diffs.toString().equals("0")) {
                    date.visibility = View.GONE
                }
            }
        }
    }
}