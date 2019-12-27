package com.with.app.ui.postlist.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class RegionListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_region_profile : ImageView = view.findViewById(R.id.img_region_profile)
    val tv_region_name : TextView = view.findViewById(R.id.tv_region_name)

    fun bind(region : RegionListItem) {
        tv_region_name.text = region.name

//        Glide.with(itemView)
//            .load(region.profile_url)
//            .into(img_region_profile)

        //buletin 클릭 이벤트
//        itemView.setOnClickListener{
//
//        }
    }
}