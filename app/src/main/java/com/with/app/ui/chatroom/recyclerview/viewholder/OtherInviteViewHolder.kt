package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.with.app.R
import com.with.app.data.AdapterPassData
import com.with.app.data.ChatVO
import com.with.app.data.ChatUserVO
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_INVITE
import com.with.app.util.addSingleListener
import com.with.app.util.toSpanned
import java.text.SimpleDateFormat
import java.util.*

class OtherInviteViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img : ImageView = view.findViewById(R.id.iv_tripimage)
    val msg : TextView = view.findViewById(R.id.tv_message)
    val date : TextView = view.findViewById(R.id.tv_date)
    val accept : Button = view.findViewById(R.id.btn_accept)

    fun bind(
        data: ChatVO,
        next: ChatVO,
        last: Boolean,
        passData: AdapterPassData
        ) {
        msg.text = data.msg?.toSpanned()
        date.text = data.date?.substring(13)

        val references : DatabaseReference = FirebaseDatabase.getInstance().reference
        val chatReference : DatabaseReference = references.child("conversations").child(passData.chatRoomId!!)
        val usersReference : DatabaseReference = references.child("users")
        var value : ChatUserVO = ChatUserVO()


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

        usersReference.child("${passData.otherIdx}/${passData.chatRoomId}").addSingleListener(
            onDataChange = {
                    snap ->
                value = snap.getValue(ChatUserVO::class.java)!!
            }
        )

        accept.setOnClickListener {
            val now = Calendar.getInstance().time
            val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
            val nowDate = pattern.format(now)
            val vo = ChatVO(MY_INVITE, "동행 성사 메시지입니다.", passData.myIdx, nowDate)

            value.lastMessage = "동행 성사 메시지입니다."
            value.lastTime = nowDate

            chatReference.push().setValue(vo)
            usersReference.child("${passData.myIdx}").child("${passData.chatRoomId}").setValue(value)
            value.unSeenCount++
            usersReference.child("${passData.otherIdx}").child("${passData.chatRoomId}").setValue(value)

            accept.visibility = View.GONE
        }
    }
}