package com.with.app.ui.chatroom.recyclerview.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO
import java.text.SimpleDateFormat
import java.util.*

class DateViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val date : TextView = view.findViewById(R.id.tv_date)

    fun bind(data : ChatVO) {
        val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
        val time = pattern.parse(data.date)
        val cal = Calendar.getInstance()
        cal.time = time
        val dayNum = cal.get(Calendar.DAY_OF_WEEK)
        var day = ""
        when (dayNum) {
            1 -> day = "일요일"
            2 -> day = "월요일"
            3 -> day = "화요일"
            4 -> day = "수요일"
            5 -> day = "목요일"
            6 -> day = "금요일"
            7 -> day = "토요일"
        }
        date.text = "${data.date?.substring(0,data.date?.lastIndexOf("일")!! + 1)} $day"
    }
}