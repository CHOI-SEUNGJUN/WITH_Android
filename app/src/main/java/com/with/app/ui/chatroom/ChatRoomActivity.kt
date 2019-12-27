package com.with.app.ui.chatroom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.with.app.R
import com.with.app.data.ChatVO
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_CHAT
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_INVITE
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.OTHER_CHAT
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.OTHER_COMPLETE
import kotlinx.android.synthetic.main.activity_chat_room.*
import java.text.SimpleDateFormat
import java.util.*


class ChatRoomActivity : AppCompatActivity() {
    private lateinit var lm : LinearLayoutManager
    private lateinit var adapter : ChatRoomAdapter

    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        init()
    }

    private fun init() {
        lm = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        adapter = ChatRoomAdapter()
        rv_chat.layoutManager = lm
        rv_chat.adapter = adapter
        rv_chat.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            rv_chat.scrollToPosition(rv_chat.adapter!!.itemCount - 1)
        } // Keyboard가 레이아웃 가리는 부분을 Recyclerview의 스크롤 위치를 조정시킴

        adapter.setMessagesWithNotify(dummy())
        rv_chat.scrollToPosition(rv_chat.adapter!!.itemCount - 1) // 첫 접속시 리싸이클러뷰가 상단에 올라가기 때문.
        sendMessage()
    }

    private fun chat() {
        reference = FirebaseDatabase.getInstance().getReference("conversations").child("김은별")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("ValueEventListener", p0.toString())
            }

            override fun onDataChange(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun sendMessage() {
        btn_send.setOnClickListener {
            if (edt_chat.text.toString() == "")
                return@setOnClickListener

            val now = Calendar.getInstance().time
            val pattern = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
            val nowDate = pattern.format(now)

            adapter.addMessageWithNotify(ChatVO(MY_CHAT, "나", edt_chat.text.toString(), "", nowDate, ""))
            edt_chat.setText("")
            rv_chat.scrollToPosition(rv_chat.adapter!!.itemCount - 1) // 아이템을 추가시켰으니 다시 스크롤 조
        }
    }

    private fun dummy(): MutableList<ChatVO> {
        // type, name, msg, userid, date, profile
        return mutableListOf(
            ChatVO(OTHER_CHAT, "김은별", "안녕하세요. 맥주 맛있나요?", "", "2019년 12월 24일 21:10", ""),
            ChatVO(OTHER_CHAT, "김은별", "설거지 잘하는데요?", "", "2019년 12월 25일 21:12", ""),
            ChatVO(OTHER_CHAT, "김은별", "ㅡ", "", "2019년 12월 25일 21:12", ""),
            ChatVO(OTHER_CHAT, "김은별", "ㅡ대답좀요", "", "2019년 12월 25일 21:13", ""),
            ChatVO(MY_CHAT, "나", "네 대답할게요;", "", "2019년 12월 25일 21:14", ""),
            ChatVO(MY_CHAT, "나", "설거지 잘하세요?", "", "2019년 12월 25일 21:14", ""),
            ChatVO(MY_CHAT, "나", "ㅈ박해ㅔㅂ잗헤ㅐㅂ잗해ㅔㅂ잗헤ㅐㅏㅇ네ㅐㄷ합ㅈ데ㅐ하데잽하ㅐㅔㅈㄷㅂ하ㅔㅐㅂㅈ다ㅐ헵잗ㅎㅂ잗해ㅔ자해ㅔㅈ받해ㅔㅂ자데ㅐ하ㅐㅔㅇㄴ마페ㅐ~~~~~~긴거테스트;", "", "2019년 12월 25일 21:15", ""),
            ChatVO(MY_CHAT, "나", "메시지 보낼게요", "", "2019년 12월 25일 21:15", ""),
            ChatVO(MY_INVITE, "나", "은별님과의 동행을 신청하셨습니다.", "", "2019년 12월 25일 21:15", ""),
            ChatVO(OTHER_COMPLETE, "김은별", "은별님이 동행을 수락하셨습니다.", "", "2019년 12월 25일 21:18", "")
        )
    }
}