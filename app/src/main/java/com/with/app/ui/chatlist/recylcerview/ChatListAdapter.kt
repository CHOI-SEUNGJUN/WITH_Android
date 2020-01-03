package com.with.app.ui.chatlist.recylcerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.local.ChatListVO

class ChatListAdapter(private val data : MutableList<ChatListVO>) : RecyclerView.Adapter<ChatListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ChatListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(data[position])
        holder.setIsRecyclable(false)
    }

}