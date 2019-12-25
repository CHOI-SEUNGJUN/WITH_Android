package com.with.app.ui.home.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class RecentBulletinViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_bulletin_profile : ImageView = view.findViewById(R.id.img_bulletin_profile)
    val tv_bulletin_name : TextView = view.findViewById(R.id.tv_bulletin_name)
    val tv_bulletin_desc : TextView = view.findViewById(R.id.tv_bulletin_desc)
    val tv_bulletin_place : TextView = view.findViewById(R.id.tv_bulletin_place)

    fun bind(bulletin : RecentBulletinItem) {
        tv_bulletin_name.text = bulletin.name
        tv_bulletin_desc.text = bulletin.desc
        tv_bulletin_place.text = bulletin.place

//        Glide.with(itemView)
//            .load(bulletin.profile_url)
//            .into(img_bulletin_profile)

        //buletin 클릭 이벤트
//        itemView.setOnClickListener{
//
//        }
    }
}