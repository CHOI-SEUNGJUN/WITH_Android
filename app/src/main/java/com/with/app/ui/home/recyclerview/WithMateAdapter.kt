package com.with.app.ui.home.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseChatListArrayData

class WithMateAdapter (private val context : Context) : RecyclerView.Adapter<WithMateViewHolder>() {
    var mate = listOf<ResponseChatListArrayData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithMateViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_mate, parent, false)

        return WithMateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mate.size
    }

    override fun onBindViewHolder(holder: WithMateViewHolder, position: Int) {
        holder.bind(mate[position], context)
    }
}