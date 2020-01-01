package com.with.app.ui.chatlist.recylcerview

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatListVO
import com.with.app.ui.chatroom.ChatRoomActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ChatListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private lateinit var convertTime : Date

    val profile : CircleImageView = view.findViewById(R.id.img_profile)
    val tv_name : TextView = view.findViewById(R.id.tv_name)
    val tv_title : TextView = view.findViewById(R.id.tv_title)
    val iv_dot : ImageView = view.findViewById(R.id.iv_dot)
    val tv_message : TextView = view.findViewById(R.id.tv_message)
    val tv_date : TextView = view.findViewById(R.id.tv_date)
    val tv_chat_remain : TextView = view.findViewById(R.id.tv_chat_remain)

    fun bind(data : ChatListVO) {
        tv_name.text = data.name
        tv_title.text = data.title
        tv_message.text = data.response.lastMessage


        tv_date.text = data.response.lastTime
        val pattern = SimpleDateFormat("yyyy년 MM월 dd일")
        pattern.parse(data.response.lastTime!!).let {
            if (it != null) {
                convertTime = it
                val nowTime : Date = Calendar.getInstance().time
                val diffs = (nowTime.time - convertTime.time) / (24 * 60 * 60 * 1000) // minutes 단위

                if (diffs.toInt() == 0) tv_date.text = data.response.lastTime?.substring(14)
                else tv_date.text = data.response.lastTime?.substring(6,13)
            } else tv_date.text = data.response.lastTime?.substring(14)
        }

        when {
            data.response.unSeenCount <= 0 -> {
                tv_chat_remain.visibility = View.GONE
            }
            data.response.unSeenCount > 99 -> {
                tv_chat_remain.text = "99+"
            }
            else -> {
                tv_chat_remain.text = data.response.unSeenCount.toString()
            }
        }

        itemView.setOnClickListener {
            itemView.context.startActivity(Intent(itemView.context, ChatRoomActivity::class.java))
        }

    }
}