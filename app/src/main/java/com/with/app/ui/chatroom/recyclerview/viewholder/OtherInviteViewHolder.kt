package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.with.app.R
import com.with.app.data.ChatVO
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_CHAT
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_INVITE
import java.text.SimpleDateFormat
import java.util.*

class OtherInviteViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img : ImageView = view.findViewById(R.id.iv_tripimage)
    val msg : TextView = view.findViewById(R.id.tv_message)
    val date : TextView = view.findViewById(R.id.tv_date)
    val accept : Button = view.findViewById(R.id.btn_accept)
    private  val reference : DatabaseReference = FirebaseDatabase.getInstance().getReference("conversations").child("chat01")


    fun bind(
        data: ChatVO,
        next: ChatVO,
        last: Boolean,
        myId: String
    ) {
        msg.text = data.msg
        date.text = data.date?.substring(13)

        if (!last) {
            if ((next.type == 1 || next.type == 3 || next.type == 4)) {
                val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
                val now_date = pattern.parse(data.date)
                val next_date = pattern.parse(next.date)
                val diffs = (next_date.time - now_date.time) / (60 * 1000) // minutes 단위

                if (diffs.toString() == "0") {
                    date.visibility = View.GONE
                }
            }
        }

        accept.setOnClickListener {
            val now = Calendar.getInstance().time
            val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
            val nowDate = pattern.format(now)
            val vo = ChatVO(MY_INVITE, "", "완료", myId, nowDate)
            reference.push().setValue(vo)
            accept.isClickable = false
        }
    }
}