package com.with.app.ui.postlist.recylcerview

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.with.app.R
import com.with.app.data.remote.ResponseSearchBoardArrayData
import com.with.app.ui.detailpost.DetailPostActivity

class PostListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val img_profile: ImageView = view.findViewById(R.id.img_profile)
    val txt_region: TextView = view.findViewById(R.id.txt_region)
    val txt_date: TextView = view.findViewById(R.id.txt_date)
    val txt_time: TextView = view.findViewById(R.id.txt_time)
    val txt_title: TextView = view.findViewById(R.id.txt_title)
    val txt_participant: TextView = view.findViewById(R.id.txt_participant)

    fun bind(data: ResponseSearchBoardArrayData) {

        Glide.with(itemView).load(data.userImg).into(img_profile)
        txt_region.text = data.regionName
        txt_date.text = data.startDate+" ~ "+data.endDate
        txt_time.text = data.uploadTime
        txt_title.text = data.title
        txt_participant.text = data.withNum.toString()

        itemView.setOnClickListener {
            //아이템 클릭 시 상세 게시글로 넘어가야 함 BOARDIDX값 불러와서 넘겨주기

            val intent = Intent(itemView.context, DetailPostActivity::class.java)
            intent.putExtra("boardIdx", data.boardIdx)
            itemView.context.startActivity(intent)
        }
    }
}
