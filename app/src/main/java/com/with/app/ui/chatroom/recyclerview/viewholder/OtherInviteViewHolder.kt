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
}