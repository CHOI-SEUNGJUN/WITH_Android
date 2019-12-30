package com.with.app.ui.postlist.recyclerview

import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class RegionListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val img_region_profile : ImageView = view.findViewById(R.id.img_signup_profile)
    val tv_region_name : TextView = view.findViewById(R.id.tv_region_name)

    fun bind(region : RegionListItem) {
        tv_region_name.text = region.name

//        Glide.with(itemView)
//            .load(region.profile_url)
//            .into(img_region_profile)

        //buletin 클릭 이벤트
        itemView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action) {
                    MotionEvent.ACTION_CANCEL -> {
                        Log.v("list", "터치 cancel")
                        itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                    }
                    MotionEvent.ACTION_DOWN -> {
                        itemView.setBackgroundColor(Color.parseColor("#4DFD9F08"))
                        Log.v("list", "터치 on")
                    }

                    MotionEvent.ACTION_MOVE -> {
                        Log.v("list", "터치 move")
                    }
                    MotionEvent.ACTION_UP -> {
                        itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                        Log.v("list", "터치 off")
                    }
                }
                return true
            }
        })
    }
}