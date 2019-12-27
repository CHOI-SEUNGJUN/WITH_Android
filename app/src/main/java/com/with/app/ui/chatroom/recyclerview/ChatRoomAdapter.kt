package com.with.app.ui.chatroom.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.ChatVO
import com.with.app.data.isChat
import com.with.app.data.isOtherChat
import com.with.app.data.isOtherName
import com.with.app.ui.chatroom.recyclerview.viewholder.*
import com.with.app.util.isDiffDay
import com.with.app.util.parseDate

class ChatRoomAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data : MutableList<ChatVO> = mutableListOf()
        set(value) {
            val trashData = mutableListOf<ChatVO>()
            trashData.add(value[0].copy(type = DATE))
            if (value[0].isOtherChat()) trashData.add(value[0].copy(type = OTHER_PROFILE))

            value.foldIndexed(value[0]) { index, prev, current ->
                if (index != 0 && current.isChat() && prev.isChat()) {
                    val now = current.date.orEmpty().parseDate()
                    val prevDate = prev.date.orEmpty().parseDate()

                    if (now.isDiffDay(prevDate)) {
                        trashData.add(current.copy(type = DATE))
                    }

                    if (current.isOtherName(prev) && current.isOtherChat()) {
                        trashData.add(current.copy(type = OTHER_PROFILE))
                    }
                }
                trashData.add(current)
                current
            }
            field = trashData
        }

    fun addMessageUiUpdate(current : ChatVO, prev : ChatVO) {
        if (current.isChat() && prev.isChat()) {
            val now = current.date.orEmpty().parseDate()
            val prevDate = prev.date.orEmpty().parseDate()

            if (now.isDiffDay(prevDate)) {
                data.add(current.copy(type = DATE))
            }

            if (current.isOtherName(prev) && current.isOtherChat()) {
                data.add(current.copy(type = OTHER_PROFILE))
            }
        }
        data.add(current)
    }

    fun setMessagesWithNotify(chats: MutableList<ChatVO>) {
        data = chats
        notifyDataSetChanged()
    }

    fun addMessageWithNotify(chatVO: ChatVO) {
        addMessageUiUpdate(chatVO, data.last())
        notifyDataSetChanged()
    }

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
            OTHER_CHAT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_other_chat, parent, false)

                ChatOtherViewHolder(view)
            }
            MY_INVITE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_my_invite, parent, false)

                MyInviteViewHolder(view)
            }
            OTHER_INVITE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_other_invite, parent, false)

                OtherInviteViewHolder(view)
            }
            OTHER_COMPLETE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_other_complete, parent, false)

                OtherCompleteViewHolder(view)
            }
            OTHER_PROFILE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_other_profile, parent, false)

                OtherProfileViewHolder(view)
            }
            DATE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date, parent, false)

                DateViewHolder(view)
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
                holder.setIsRecyclable(false)
                if (position == data.size-1) {
                    holder.bind(data[position], data[position], true)
                } else {
                    holder.bind(data[position], data[position+1], false)
                }
            }
            is ChatOtherViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) {
                    holder.bind(data[position], data[position], true)
                } else {
                    holder.bind(data[position], data[position+1], false)
                }
            }
            is MyInviteViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) {
                    holder.bind(data[position], data[position], true)
                } else {
                    holder.bind(data[position], data[position+1], false)
                }            }
            is OtherInviteViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) {
                    holder.bind(data[position], data[position], true)
                } else {
                    holder.bind(data[position], data[position+1], false)
                }            }
            is OtherCompleteViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) {
                    holder.bind(data[position], data[position], true)
                } else {
                    holder.bind(data[position], data[position+1], false)
                }
            }
            is OtherProfileViewHolder -> {
                holder.setIsRecyclable(false)
                holder.bind(data[position])
            }
            is DateViewHolder -> {
                holder.setIsRecyclable(false)
                holder.bind(data[position])
            }

        }
    }

    companion object {
        const val MY_CHAT = 0 // 내 채팅
        const val OTHER_CHAT = 1 // 다른 사람 채팅
        const val MY_INVITE = 2 // 내가 초대장을 보낸 뷰 + 성사 완료 뷰
        const val OTHER_INVITE = 3 // 다른사람이 초대장 받는 뷰
        const val OTHER_COMPLETE = 4 // 다른사람 화면에 성사 완료 뷰
        const val OTHER_PROFILE = 5 // 다른사람 프로필 뷰
        const val DATE = 6 // 날짜
    }
}