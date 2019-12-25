package com.with.app.ui.home.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class WithMateViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_mate_profile : ImageView = view.findViewById(R.id.img_mate_profile)
    val tv_mate_name : TextView = view.findViewById(R.id.tv_mate_name)

    fun bind(mate : WithMateItem) {
        tv_mate_name.text = mate.name

//        Glide.with(itemView)
//            .load(mate.profile_url)
//            .into(img_mate_profile)

        //mate 클릭 이벤트
//        itemView.setOnClickListener{
//
//        }
    }
}