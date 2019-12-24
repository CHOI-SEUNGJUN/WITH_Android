package com.with.app

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.with.app.data.PostItem
import kotlinx.android.synthetic.main.fragment_post_list.*


class PostListFragment : Fragment() {

    private lateinit var rvPostList : RecyclerView
    private lateinit var postListAdapter : PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        GetPostListData(view)
        return view
    }

    private fun GetPostListData(v: View) {

        rvPostList = v.findViewById(R.id.rv_postList)
        postListAdapter = PostListAdapter(context!!)

        rvPostList.adapter = postListAdapter
        rvPostList.layoutManager = LinearLayoutManager(context)

        postListAdapter.data = listOf(
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            )
        )
        postListAdapter.notifyDataSetChanged()
    }
}
