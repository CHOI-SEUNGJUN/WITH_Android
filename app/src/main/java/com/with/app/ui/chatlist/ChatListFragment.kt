package com.with.app.ui.chatlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.with.app.R
import com.with.app.data.ChatListVO
import com.with.app.data.ChatUserVO
import com.with.app.ui.chatlist.recylcerview.ChatListAdapter
import com.with.app.util.addListener
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment() {

    private var myIdx = 1

    private var value : ChatUserVO = ChatUserVO()

    private lateinit var data : MutableList<ChatListVO>
    private lateinit var adapter : ChatListAdapter
    private lateinit var lm : LinearLayoutManager
    private lateinit var reference : DatabaseReference
    private lateinit var usersReference : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        reference = FirebaseDatabase.getInstance().reference
        usersReference = reference.child("users")

        data = mutableListOf()
        fireBaseChatListener()
        adapter = ChatListAdapter(data)
        lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_chat_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_chat_list.layoutManager = lm
        rv_chat_list.adapter = adapter
    }

    private fun fireBaseChatListener() {
        usersReference.child("$myIdx").addListener(
            onChildAdded = {
                    snap, _ ->
                value = snap.getValue(ChatUserVO::class.java)!!
                data.add(ChatListVO(snap.key," ","김남수","맥주마실까요?"," ", value))
                adapter.notifyDataSetChanged()
            },
            onChildChanged = {
                snap, _ ->
                value = snap.getValue(ChatUserVO::class.java)!!
                for ( item in data ) {
                }
                data.filter {
                    it.chatRoomId == snap.key
                }.forEach{
                    it.response = value
                }
                adapter.notifyDataSetChanged()
            }
        )
    }
}
