package com.with.app.ui.postlist.recylcerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.remote.ResponseSearchBoardArrayData


class PostListAdapter (private val context: Context): RecyclerView.Adapter<PostListViewHolder>(){
    var data = listOf<ResponseSearchBoardArrayData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post_bulletin,parent,false)

        return PostListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(data[position])
    }
}