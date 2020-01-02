package com.with.app.ui.home.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseLatelyBoardArrayData


class RecentBulletinAdapter (private val context : Context) : RecyclerView.Adapter<RecentBulletinViewHolder>() {
    var bulletin = listOf<ResponseLatelyBoardArrayData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentBulletinViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_recent_bulletin, parent, false)

        return RecentBulletinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bulletin.size
    }

    override fun onBindViewHolder(holder: RecentBulletinViewHolder, position: Int) {
        holder.bind(bulletin[position], context)
    }

}