package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO
import com.with.app.extension.toSpanned
import java.text.SimpleDateFormat

class MyInviteViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img : ImageView = view.findViewById(R.id.iv_tripimage)
    val msg : TextView = view.findViewById(R.id.tv_message)
    val date : TextView = view.findViewById(R.id.tv_date)


    fun bind(data: ChatVO, next: ChatVO, last: Boolean) {
        msg.text = data.msg?.toSpanned()
        date.text = data.date?.substring(data.date?.lastIndexOf("일")!! + 2)
        if (data.msg?.contains("신청")!!) {
            img.setImageResource(R.drawable.illust_apply)
        } else {
            img.setImageResource(R.drawable.illust_complete)
        }
        if (!last) {
            if ((next.type == 0 || next.type == 2)) {
                val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
                val now_date = pattern.parse(data.date)
                val next_date = pattern.parse(next.date)
                val diffs = (next_date.time - now_date.time) / (60 * 1000) // minutes 단위

                if (diffs.toString() == "0") {
                    date.visibility = View.GONE
                }
            }
        }
    }
}