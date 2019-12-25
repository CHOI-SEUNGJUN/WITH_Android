package com.with.app.ui.home.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R

class RecPlaceAdapter (private val context : Context) : RecyclerView.Adapter<RecPlaceViewHolder>() {
    var recPlace = listOf<RecPlaceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecPlaceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend_place, parent, false)

        return RecPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recPlace.size
    }

    override fun onBindViewHolder(holder: RecPlaceViewHolder, position: Int) {
        holder.bind(recPlace[position])
    }
}