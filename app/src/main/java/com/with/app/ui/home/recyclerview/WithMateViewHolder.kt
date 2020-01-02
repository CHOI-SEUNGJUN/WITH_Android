package com.with.app.ui.home.recyclerview

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseChatListArrayData
import com.with.app.extension.load
import com.with.app.ui.chatlist.recylcerview.ChatListViewHolder
import com.with.app.ui.chatroom.ChatRoomActivity

class WithMateViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_mate_profile : ImageView = view.findViewById(R.id.img_mypage_profile)
    val tv_mate_name : TextView = view.findViewById(R.id.tv_mate_name)

    fun bind(mate : ResponseChatListArrayData) {
        tv_mate_name.text = mate.name
        img_mate_profile.load(itemView, mate.userImg)

        itemView.setOnClickListener{
            val intent = Intent(itemView.context, ChatRoomActivity::class.java)
            intent.putExtra("mode", ChatListViewHolder.CHATLISTTOCHAT)
            intent.putExtra("userIdx", mate.userIdx)
            intent.putExtra("boardIdx", mate.boardIdx)
            intent.putExtra("writeUserIdx", mate.roomId.split("_")[1])
            intent.putExtra("regionName", mate.regionName)
            intent.putExtra("startDate", mate.startDate)
            intent.putExtra("endDate", mate.endDate)
            intent.putExtra("title", mate.title)
            intent.putExtra("userImg", mate.userImg)
            intent.putExtra("name", mate.name)
            intent.putExtra("withFlag", mate.withFlag)
            intent.putExtra("writerImg", mate.writerImg)
            intent.putExtra("senderUserIdx", mate.roomId.split("_")[2])
            itemView.context.startActivity(intent)
        }
    }
}