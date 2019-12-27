package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO
import de.hdodenhof.circleimageview.CircleImageView

class OtherProfileViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val name : TextView = view.findViewById(R.id.tv_name)
    val profile : CircleImageView = view.findViewById(R.id.iv_profile)

    fun bind(data : ChatVO) {
        name.text = data.name
    }
}