package com.with.app.ui.home.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class RecPlaceViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_recommend_place : ImageView = view.findViewById(R.id.img_recommend_place)
    val tv_recommend_place : TextView = view.findViewById(R.id.tv_recommend_place)

    fun bind(recPlace : RecPlaceItem) {
        tv_recommend_place.text = recPlace.place

        img_recommend_place.clipToOutline = true

//        Glide.with(itemView)
//            .load(recPlace.place_url)
//            .into(img_recommend_place)

        //recPlace 클릭 이벤트
//        itemView.setOnClickListener{
//
//        }
    }
}