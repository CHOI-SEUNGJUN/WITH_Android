package com.with.app.ui.postlist.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseCountryListArrayData

class RegionListAdapter (private val context : Context) : RecyclerView.Adapter<RegionListViewHolder>() {
    var region = listOf<ResponseCountryListArrayData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionListViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_region_list, parent, false)
        return RegionListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return region.size
    }

    override fun onBindViewHolder(holder: RegionListViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(region[position])
    }

}