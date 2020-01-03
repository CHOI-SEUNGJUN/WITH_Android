package com.with.app.ui.chatroom

import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.with.app.R
import com.with.app.data.local.*
import com.with.app.manage.RequestManager
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_CHAT
import com.with.app.ui.chatroom.recyclerview.ChatRoomAdapter.Companion.MY_INVITE
import com.with.app.ui.detailpost.DetailPostActivity
import com.with.app.ui.detailpost.DetailPostActivity.Companion.POSTINGTOCHAT
import com.with.app.extension.*
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.dialog_invite.view.*
import org.koin.android.ext.android.inject

class ChatRoomActivity : AppCompatActivity() {

    private val requestManager: RequestManager by inject()

    private lateinit var adapter: ChatRoomAdapter

    private var value: ChatUserVO =
        ChatUserVO()
    private var tempValue: ChatUserVO =
        ChatUserVO()
    private var passData: AdapterPassData =
        AdapterPassData()

    private var otherCount: Int = 0

    // AuthManager에서 받아와야함
    private var myIdx = requestManager.authManager.idx
    // 서버에서 받아와야함
    private var boardIdx: Int = 0
    private var otherIdx = 0
    private var otherName = ""
    private var otherProfile = ""
    // 채팅하기 눌렀을때 불러와야함
    private var posterIdx = 0
    private var senderIdx = 0

    private var meetDate = ""
    private var chatRoomId = ""

    private var inviteFlag = 0
    private var withFlag = -1
    private var textDate = ""

    private lateinit var reference: DatabaseReference
    private lateinit var chatReference: DatabaseReference
    private lateinit var usersReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        init()
    }

    private fun init() {
        if(!requestManager.authManager.flag) {
            cl_tutorial.visible()
            edt_chat.isEnabled = false
            layout_toolbar.isEnabled = false
            layout_post.isEnabled = false
            layout_send.isEnabled = false
        }

        img_tutorial_cancle.setOnClickListener {
            cl_tutorial.gone()
            edt_chat.isEnabled = true
            requestManager.authManager.flag = true
            edt_chat.isEnabled = true
            layout_toolbar.isEnabled = true
            layout_post.isEnabled = true
            layout_send.isEnabled = true
        }

        setBasicData()

        reference = FirebaseDatabase.getInstance().reference
        chatReference = reference.child("conversations").child(chatRoomId)
        usersReference = reference.child("users")

        fireBaseChatListener()
        sendMessage()
        inviteMessage()

        Log.e("withFlag", withFlag.toString())
        passData = AdapterPassData(myIdx, otherIdx, otherName, otherProfile, chatRoomId, boardIdx, withFlag)

        adapter = ChatRoomAdapter(passData, requestManager)
        rv_chat.setLinearLayoutManager(applicationContext)
        rv_chat.adapter = adapter
        rv_chat.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            rv_chat.adjustScroll()
        } // Keyboard가 레이아웃 가리는 부분을 Recyclerview의 스크롤 위치를 조정시킴

        rv_chat.adjustScroll() // 첫 접속시 리싸이클러뷰가 상단에 올라가기 때문.
    }

    private fun setBasicData() {
        otherName = intent.getStringExtra("name")
        tv_name.text = otherName
        tv_region.text = intent.getStringExtra("regionName")
        boardIdx = intent.getIntExtra("boardIdx", 0)
        tv_title.text = intent.getStringExtra("title")
        textDate = "${intent.getStringExtra("startDate")} ~ ${intent.getStringExtra("endDate")}"
        tv_date.text = textDate

        otherProfile = intent.getStringExtra("userImg")

        setBoardIdx(value, tempValue, boardIdx)

        posterIdx = intent.getStringExtra("writeUserIdx").toInt()
        senderIdx = intent.getStringExtra("senderUserIdx").toInt()
        withFlag = intent.getIntExtra("withFlag", -1)
        chatRoomId = "${boardIdx}_${posterIdx}_${senderIdx}"

        val mode = intent.getIntExtra("mode", 0)

        if (mode == POSTINGTOCHAT) {
            otherIdx = posterIdx
            iv_profile.load(application, otherProfile)
        } else {
            otherIdx = intent.getIntExtra("userIdx", 0)
            iv_profile.load(application, intent.getStringExtra("writerImg"))
        }

        btn_more.setOnClickListener {
            val intent = Intent(this, DetailPostActivity::class.java)
            intent.putExtra("boardIdx", boardIdx)
            startActivity(intent)
        }

        if (posterIdx != myIdx) btn_invite.gone()

        if (withFlag == 1) {
            btn_invite.setImageResource(R.drawable.send_invitation_unselected_btn)
            btn_invite.isEnabled = false
        }
    }

    private fun inviteMessage() {
        btn_invite.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_invite, null)

            val dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()

            view.apply {
                tv_curDate.text = textDate
                tv_otherName.text = otherName

                btn_close.setOnClickListener { dialog.cancel() }
                btn_goWith.setOnClickListener {
                    meetDate = "${dp_with.year}년 ${dp_with.month + 1}월 ${dp_with.dayOfMonth}일"
                    val nowDate = returnNowDate()
                    val chatVO = ChatVO(MY_INVITE, "동행 신청 메시지입니다.-${meetDate}", myIdx, nowDate)

                    setLastMessage(value, tempValue, "동행 신청 메시지입니다.")
                    setLastTime(value, tempValue, nowDate)
                    setInviteFlag(value, tempValue, 1)
                    chatPush(chatVO)

                    dialog.cancel()
                }
            }
        }
    }

    private fun sendMessage() {
        btn_send.setOnClickListener {
            if (edt_chat.text.toString().isBlank())
                return@setOnClickListener

            val chatVO = ChatVO(MY_CHAT, edt_chat.text.toString(), myIdx, returnNowDate())

            setLastMessage(value, tempValue, edt_chat.text.toString())
            setLastTime(value, tempValue, returnNowDate())

            chatPush(chatVO)

            edt_chat.setText("")
            rv_chat.adjustScroll() // 아이템을 추가시켰으니 다시 스크롤 조정
        }
    }

    private fun fireBaseChatListener() {
        chatReference.addListener(
            onChildAdded = { snap, _ ->
                adapter.addMessageWithNotify(snap.getValue(ChatVO::class.java)!!)
                rv_chat.adjustScroll()
            })

        usersReference.child("$otherIdx/$chatRoomId").addSingleListener(
            onDataChange = { snap ->
                snap.getValue(ChatUserVO::class.java).let {
                    if (it != null) value = it
                }
                snap.getValue(ChatUserVO::class.java).let {
                    if (it != null) tempValue = it
                }
            })

        usersReference.child("$otherIdx/$chatRoomId").addListener(
            onChildAdded = { snap, _ ->
                if (snap.key == "unSeenCount") otherCount = snap.value.toString().toInt()
                if (snap.key == "inviteFlag") inviteFlag = snap.value.toString().toInt()
                if (inviteFlag == 1) {
                    btn_invite.setImageResource(R.drawable.send_invitation_unselected_btn)
                    btn_invite.isEnabled = false
                }
            },
            onChildChanged = { snap, _ ->
                if (snap.key == "unSeenCount") otherCount = snap.value.toString().toInt()
                if (snap.key == "inviteFlag") inviteFlag = snap.value.toString().toInt()
                if (inviteFlag == 1) {
                    btn_invite.setImageResource(R.drawable.send_invitation_unselected_btn)
                    btn_invite.isEnabled = false
                }
            })

        // 채팅방 입장시 카운트 초기화
        if (!value.lastMessage.isNullOrBlank())
            resetCount()
    }

    override fun onPause() {
        super.onPause()
        resetCount()
    }

    override fun onDestroy() {
        super.onDestroy()
        resetCount()
    }

    private fun chatPush(chatVO : ChatVO) {
        value.unSeenCount = 0
        chatReference.push().setValue(chatVO)
        usersReference.child("$myIdx").child(chatRoomId).setValue(value)
        tempValue.unSeenCount = otherCount + 1
        usersReference.child("$otherIdx").child(chatRoomId).setValue(tempValue)
    }

    private fun resetCount() {
        usersReference.child("$myIdx").child(chatRoomId).child("unSeenCount").setValue(0)
    }
}