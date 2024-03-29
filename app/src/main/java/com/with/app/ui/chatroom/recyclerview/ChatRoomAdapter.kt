package com.with.app.ui.chatroom.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.with.app.R
import com.with.app.data.local.*
import com.with.app.data.remote.RequestWithInviteData
import com.with.app.manage.RequestManager
import com.with.app.ui.chatroom.recyclerview.viewholder.*
import com.with.app.extension.*

class ChatRoomAdapter(private val passData: AdapterPassData, private val requestManager: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var myId = passData.myIdx
    private var otherName = passData.otherName

    private var inviteCheckPosition = -1

    private var data : MutableList<ChatVO> = mutableListOf()
        set(value) {
            val trashData = mutableListOf<ChatVO>()
            trashData.add(value[0].copy(type = DATE))
            if (value[0].isOtherChat()) trashData.add(value[0].copy(type = OTHER_PROFILE))

            value.foldIndexed(value[0]) { index, prev, current ->
                if (index != 0 && current.isChat() && prev.isChat()) {
                    val now = current.date.orEmpty().parseDate()
                    val prevDate = prev.date.orEmpty().parseDate()

                    if (now.isDiffDay(prevDate))
                        trashData.add(current.copy(type = DATE))

                    if (current.isOtherName(prev) && current.isOtherChat())
                        trashData.add(current.copy(type = OTHER_PROFILE))
                }
                trashData.add(current)
                current
            }
            field = trashData
        }

    private fun addMessageUiUpdate(current : ChatVO, prev : ChatVO) {
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
        notifyDataSetChanged()
    }

    fun addMessageWithNotify(chatVO: ChatVO) {
        if (chatVO.isMessage() && chatVO.isSameName(myId)) chatVO.type = MY_CHAT
        if (chatVO.isMessage() && chatVO.isOtherName(myId)) chatVO.type = OTHER_CHAT
        if (chatVO.isInviteApply() && chatVO.isSameName(myId)) {
            chatVO.type = MY_INVITE
            var temp = chatVO.msg
            temp = temp?.replace("동행 신청 메시지입니다.-", "")
            chatVO.msg = "<font color=\"#311a80\"><b>${temp}<br>${otherName}</font></b>님과의<br>동행을 신청하셨습니다."
        }
        if (chatVO.isInviteApply() && chatVO.isOtherName(myId)) {
            chatVO.type = OTHER_INVITE
            var temp = chatVO.msg
            temp = temp?.replace("동행 신청 메시지입니다.-", "")
            chatVO.msg = "<font color=\"#311a80\"><b>${temp}<br>${otherName}</font></b>님이<br>동행을 신청하셨습니다."
        }
        if (chatVO.isInviteComplete() && chatVO.isSameName(myId)) {
            chatVO.type = MY_INVITE
            var temp = chatVO.msg
            temp = temp?.replace("동행 성사 메시지입니다.-", "")
            chatVO.msg = "<font color=\"#311a80\"><b>${temp}<br>${otherName}</font></b>님의<br>동행을 수락하셨습니다."
        }
        if (chatVO.isInviteComplete() && chatVO.isOtherName(myId)) {
            chatVO.type = OTHER_COMPLETE
            var temp = chatVO.msg
            temp = temp?.replace("동행 성사 메시지입니다.-", "")
            chatVO.msg = "<font color=\"#311a80\"><b>${temp}<br>${otherName}</font></b>님이<br>동행을 수락하셨습니다."
        }

        if (data.isEmpty()) {
            data.add(chatVO.copy(type = DATE))
            if(chatVO.isOtherName(myId)) data.add(chatVO.copy(type = OTHER_PROFILE))
            data.add(chatVO)
            notifyDataSetChanged()
        } else addMessageUiUpdate(chatVO, data.last())
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
            else -> viewHolder
            }
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ChatMyViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) holder.bind(data[position], data[position], true)
                else holder.bind(data[position], data[position+1], false)
            }
            is ChatOtherViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) holder.bind(data[position], data[position], true)
                else holder.bind(data[position], data[position+1], false)
            }
            is MyInviteViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) holder.bind(data[position], data[position], true)
                else holder.bind(data[position], data[position+1], false)
            }
            is OtherInviteViewHolder -> {
                holder.setIsRecyclable(false)
                if (position != data.size-1) {
                    if (data[position+1].isOtherChat() && isDiffDays(data[position].date, data[position+1].date))
                        holder.date.gone()
                }

                holder.msg.text = data[position].msg?.toSpanned()
                var temp = data[position].msg
                temp = temp?.replace("<font color=\"#311a80\"><b>", "")
                val tempIndex : Int = temp?.lastIndexOf("일")!!
                temp = temp.substring(0, tempIndex+1)

                holder.date.text = data[position].date?.substring(13)

                val references : DatabaseReference = FirebaseDatabase.getInstance().reference
                val chatReference : DatabaseReference = references.child("conversations").child(passData.chatRoomId!!)
                val usersReference : DatabaseReference = references.child("users")
                var value = ChatUserVO()
                var tempValue = ChatUserVO()
                var otherCount = 0

                usersReference.child("${passData.otherIdx}/${passData.chatRoomId}").addSingleListener(
                    onDataChange = { snap ->
                        snap.getValue(ChatUserVO::class.java).let {
                            if (it != null) value = it
                        }
                        snap.getValue(ChatUserVO::class.java).let {
                            if (it != null) tempValue = it
                        }
                    }
                )


                usersReference.child("${passData.otherIdx}/${passData.chatRoomId}").addListener(
                    onChildAdded = { snap, _ ->
                        if (snap.key == "unSeenCount")
                            otherCount = snap.value.toString().toInt()
                    },
                    onChildChanged = { snap, _ ->
                        if (snap.key == "unSeenCount") otherCount = snap.value.toString().toInt()
                    })

                usersReference.child("${passData.otherIdx}/${passData.chatRoomId}").addSingleListener(
                    onDataChange = { snap: DataSnapshot ->
                        value = snap.getValue(ChatUserVO::class.java)!!
                    })

                holder.accept.setOnClickListener { val vo = ChatVO(MY_INVITE, "동행 성사 메시지입니다.-${temp}", passData.myIdx, returnNowDate())
                    temp = temp!!
                        .substring(2)
                        .replace("년", ".")
                        .replace("월", ".")
                        .replace("일", "")
                        .replace(" ", "")

                    requestManager.requestWithInvite(RequestWithInviteData(passData.chatRoomId!!, temp!!))
                        .safeEnqueue(
                            onSuccess = {
                                setLastMessage(value, tempValue, "동행 성사 메시지입니다.")
                                setLastTime(value, tempValue, returnNowDate())

                                value.unSeenCount = 0
                                chatReference.push().setValue(vo)
                                usersReference.child("${passData.myIdx}").child("${passData.chatRoomId}").setValue(value)
                                tempValue.unSeenCount = otherCount + 1
                                usersReference.child("${passData.otherIdx}").child("${passData.chatRoomId}").setValue(tempValue)

                                inviteCheckPosition = position
                                notifyDataSetChanged()
                            }
                        )
                }

                if (inviteCheckPosition == position || passData.withFlag == 1){
                    holder.accept.setBackgroundResource(R.drawable.corner_cloudyblue_6dp)
                    holder.accept.setBackgroundColor(R.drawable.corner_cloudyblue_6dp)
                    holder.accept.isClickable = false
                }

            }
            is OtherCompleteViewHolder -> {
                holder.setIsRecyclable(false)
                if (position == data.size-1) holder.bind(data[position], data[position], true)
                else holder.bind(data[position], data[position+1], false)
            }
            is OtherProfileViewHolder -> {
                holder.setIsRecyclable(false)
                holder.bind(passData.otherName, passData.otherProfile)
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