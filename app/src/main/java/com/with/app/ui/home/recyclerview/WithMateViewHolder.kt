package com.with.app.ui.home.recyclerview

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseChatListArrayData
import com.with.app.extension.load

class WithMateViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_mate_profile : ImageView = view.findViewById(R.id.img_mypage_profile)
    val tv_mate_name : TextView = view.findViewById(R.id.tv_mate_name)

    fun bind(mate : ResponseChatListArrayData, context : Context) {
        tv_mate_name.text = mate.name
        img_mate_profile.load(itemView, mate.userImg)

        itemView.setOnClickListener{

        }
    }
}