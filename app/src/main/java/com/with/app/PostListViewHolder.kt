package com.with.app

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.with.app.data.PostItem

class PostListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img_profile: ImageView = view.findViewById(R.id.img_profile)
    val txt_region: TextView = view.findViewById(R.id.txt_region)
    val txt_date: TextView = view.findViewById(R.id.txt_date)
    val txt_time: TextView = view.findViewById(R.id.txt_time)
    val txt_title: TextView = view.findViewById(R.id.txt_title)
    val txt_participant: TextView = view.findViewById(R.id.txt_participant)

    fun bind(data: PostItem) {
        //Glide.with(itemView).load(data.url).into(img_profile)
        txt_region.text = data.region
        txt_date.text = data.date
        txt_time.text = data.time
        txt_title.text = data.title
        txt_participant.text = data.participant
    }
}