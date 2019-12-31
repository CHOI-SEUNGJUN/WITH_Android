package com.with.app.ui.postlist.recyclerview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.with.app.R
import com.with.app.data.remote.ResponseCountryListArrayData
import com.with.app.manage.RegionManager


class RegionListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img_region_profile : ImageView = view.findViewById(R.id.img_signup_profile)
    val tv_region_name : TextView = view.findViewById(R.id.tv_region_name)

    fun bind(region : ResponseCountryListArrayData, context: Context, regionManager: RegionManager) {

        tv_region_name.text = region.regionName

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.default_country_image)
            .fallback(R.drawable.default_country_image)
            .error(R.drawable.default_country_image)

        Glide.with(itemView)
            .load(region.regionImgS)
            .apply(requestOptions)
            .into(img_region_profile)

        //buletin 클릭 이벤트
        itemView.setOnTouchListener { _, event ->
            when(event?.action) {
                MotionEvent.ACTION_CANCEL -> {
                    itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                }
                MotionEvent.ACTION_DOWN -> {
                    itemView.setBackgroundColor(Color.parseColor("#4DFD9F08"))
                }

                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                    itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                    regionManager.code = region.regionCode
                    regionManager.name = region.regionName
                    val intent = Intent()
                    if (context is Activity) {
                        context.setResult(Activity.RESULT_OK, intent)
                        context.finish()
                    }
                }
            }
            true
        }
    }
}