package com.with.app.ui.home.recyclerview

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseLatelyBoardArrayData
import com.with.app.ui.detailpost.DetailPostActivity
import com.with.app.extension.load

class RecentBulletinViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_bulletin_profile : ImageView = view.findViewById(R.id.img_bulletin_profile)
    val tv_bulletin_name : TextView = view.findViewById(R.id.tv_bulletin_name)
    val tv_bulletin_desc : TextView = view.findViewById(R.id.tv_bulletin_desc)
    val tv_bulletin_place : TextView = view.findViewById(R.id.tv_bulletin_place)

    fun bind(bulletin : ResponseLatelyBoardArrayData, context: Context) {
        tv_bulletin_name.text = bulletin.name
        tv_bulletin_desc.text = bulletin.title
        tv_bulletin_place.text = bulletin.regionName

        img_bulletin_profile.load(itemView, bulletin.userImg)

        itemView.setOnClickListener{
            val intent = Intent(context, DetailPostActivity::class.java)
            intent.putExtra("boardIdx", bulletin.boardIdx)
            context.startActivity(intent)
        }
    }
}