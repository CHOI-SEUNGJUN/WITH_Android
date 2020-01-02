package com.with.app.ui.chatlist.recylcerview

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatListVO
import com.with.app.ui.chatroom.ChatRoomActivity
import com.with.app.extension.gone
import com.with.app.extension.load
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ChatListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private lateinit var convertTime : Date

    val profile : CircleImageView = view.findViewById(R.id.img_profile)
    val tv_name : TextView = view.findViewById(R.id.tv_name)
    val tv_title : TextView = view.findViewById(R.id.tv_title)
    val tv_message : TextView = view.findViewById(R.id.tv_message)
    val tv_date : TextView = view.findViewById(R.id.tv_date)
    val tv_chat_remain : TextView = view.findViewById(R.id.tv_chat_remain)

    fun bind(item : ChatListVO) {
        val ourServer = item.ourServer
        val fireBase = item.fireBase
        tv_name.text = ourServer.name
        tv_title.text = ourServer.title
        tv_message.text = fireBase.lastMessage

        profile.load(itemView, ourServer.userImg)

        tv_date.text = fireBase.lastTime
        val pattern = SimpleDateFormat("yyyy년 MM월 dd일")
        pattern.parse(fireBase.lastTime!!).let {
            if (it != null) {
                convertTime = it
                val nowTime : Date = Calendar.getInstance().time
                val diffs = (nowTime.time - convertTime.time) / (24 * 60 * 60 * 1000) // minutes 단위

                if (diffs.toInt() == 0) tv_date.text = fireBase.lastTime?.substring(14)
                else tv_date.text = fireBase.lastTime?.substring(6,13)
            } else tv_date.text = fireBase.lastTime?.substring(14)
        }

        when {
            fireBase.unSeenCount <= 0 -> {
                tv_chat_remain.gone()
            }
            fireBase.unSeenCount > 99 -> {
                tv_chat_remain.text = "99+"
            }
            else -> {
                tv_chat_remain.text = fireBase.unSeenCount.toString()
            }
        }

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ChatRoomActivity::class.java)
            intent.putExtra("mode", CHATLISTTOCHAT)
            intent.putExtra("boardIdx", ourServer.boardIdx)
            intent.putExtra("writeUserIdx", ourServer.roomId.split("_")[1])
            intent.putExtra("regionName", ourServer.regionName)
            intent.putExtra("startDate", ourServer.startDate)
            intent.putExtra("endDate", ourServer.endDate)
            intent.putExtra("title", ourServer.title)
            intent.putExtra("userImg", ourServer.userImg)
            intent.putExtra("name", ourServer.name)
            intent.putExtra("senderUserIdx", ourServer.roomId.split("_")[2])
            itemView.context.startActivity(intent)
        }

    }

    companion object {
        const val CHATLISTTOCHAT = 555
    }
}