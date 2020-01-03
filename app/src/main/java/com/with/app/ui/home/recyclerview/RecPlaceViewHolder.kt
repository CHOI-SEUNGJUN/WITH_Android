package com.with.app.ui.home.recyclerview

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseRecommendPlaceArrayData
import com.with.app.extension.load
import com.with.app.extension.toSpanned

class RecPlaceViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_recommend_place : ImageView = view.findViewById(R.id.img_recommend_place)
    val tv_recommend_place : TextView = view.findViewById(R.id.tv_recommend_place)

    fun bind(recPlace : ResponseRecommendPlaceArrayData, context : Context) {
        tv_recommend_place.text = recPlace.regionNameEng.toSpanned()
        img_recommend_place.clipToOutline = true

        img_recommend_place.load(itemView, recPlace.regionImgS)

        itemView.setOnClickListener{
        }
    }
}