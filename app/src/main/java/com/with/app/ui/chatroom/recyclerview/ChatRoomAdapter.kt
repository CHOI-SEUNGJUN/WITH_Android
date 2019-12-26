package com.with.app.ui.chatroom.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO
import com.with.app.ui.chatroom.recyclerview.viewholder.ChatMyViewHolder

class ChatRoomAdapter(private val data : MutableList<ChatVO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MY_CHAT = 0 // 내 채팅
    private val OTHER_CHAT = 1 // 다른 사람 채팅
    private val MY_INVITE = 2 // 내가 초대장을 보낸 뷰
    private val OTHER_INVITE = 3 // 다른사람이 초대장 받는 뷰
    private val MY_COMPLETE = 4 // 내 화면에 성사 완료 뷰
    private val OTHER_COMPLETE = 5 // 다른사람 화면에 성사 완료 뷰
    private val OTHER_PROFILE = 6 // 다른사람 프로필 뷰
    private val DATE = 7 // 데이트

    override fun getItemViewType(position: Int): Int {
        val chatData = data[position]

        return chatData.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        lateinit var viewHolder : RecyclerView.ViewHolder

        return when (viewType) {
            MY_CHAT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_my_chat, parent, false)

                ChatMyViewHolder(view)
            }
            else -> {
                    viewHolder
                }
            }
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatMyViewHolder -> {
               holder.bind(data[position])
            }
        }
    }

}