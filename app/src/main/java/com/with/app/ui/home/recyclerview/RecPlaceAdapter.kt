package com.with.app.ui.home.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseRecommendPlaceArrayData
import com.with.app.manage.RequestManager

class RecPlaceAdapter(private val context: Context, private val placeClickListener : PlaceClickListener) : RecyclerView.Adapter<RecPlaceViewHolder>() {
    var recPlace = listOf<ResponseRecommendPlaceArrayData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecPlaceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend_place, parent, false)

        return RecPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recPlace.size
    }

    override fun onBindViewHolder(holder: RecPlaceViewHolder, position: Int) {
        holder.bind(recPlace[position], placeClickListener)
    }
}