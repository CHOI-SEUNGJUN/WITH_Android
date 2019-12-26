package com.with.app.ui.chatlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.with.app.R
import com.with.app.data.ChatListVO
import com.with.app.ui.chatlist.recylcerview.ChatListAdapter
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment() {

    private lateinit var data : MutableList<ChatListVO>
    private lateinit var adapter : ChatListAdapter
    private lateinit var lm : LinearLayoutManager

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
        dummy()
        adapter = ChatListAdapter(data)
        lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_chat_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_chat_list.layoutManager = lm
        rv_chat_list.adapter = adapter
    }

    private fun dummy() {
        data = mutableListOf(
            ChatListVO(" ", "김은별", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:26", 0),
            ChatListVO(" ", "최승준", "프랑스 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:25", 102),
            ChatListVO(" ", "김루희", "런던에서 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:24", 99),
            ChatListVO(" ", "조현아", "한강에서 소주마실까?", " ", "너무춥다ㅠㅠ", "09:23", 44),
            ChatListVO(" ", "석영현", "그냥 놀아", " ", "너무춥다ㅠㅠ", "09:22", 6),
            ChatListVO(" ", "현환희", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:21", 3),
            ChatListVO(" ", "김민준", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:20", 0),
            ChatListVO(" ", "김미정", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:19", 0),
            ChatListVO(" ", "김남수", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:18", 0),
            ChatListVO(" ", "권준", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:00", 0),
            ChatListVO(" ", "권준", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:00", 0),
            ChatListVO(" ", "권", "부다페스트 맥주마실까?", " ", "너무춥다ㅠㅠ", "09:00", 25)
        )
    }
}
