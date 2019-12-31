package com.with.app.ui.recent.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class RecentSearchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tv_keyword : TextView = view.findViewById(R.id.tv_keyword)
    val btn_delete : View = view.findViewById(R.id.btn_delete)
}