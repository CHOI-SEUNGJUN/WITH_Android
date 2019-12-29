package com.with.app.ui.chatlist.recylcerview

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatListVO
import com.with.app.ui.chatroom.ChatRoomActivity
import de.hdodenhof.circleimageview.CircleImageView

class ChatListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

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
        tv_message.text = data.message
        tv_date.text = data.date
        if (data.remain == 0) {
            tv_chat_remain.visibility = View.GONE
        } else if (data.remain > 99 ){
            tv_chat_remain.text = "99+"
        } else {
            tv_chat_remain.text = data.remain.toString()
        }

        itemView.setOnClickListener {
            itemView.context.startActivity(Intent(itemView.context, ChatRoomActivity::class.java))
        }

    }
}