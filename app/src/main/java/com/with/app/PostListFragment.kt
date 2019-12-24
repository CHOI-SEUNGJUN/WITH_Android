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

        postListAdapter = PostListAdapter(context!!)
       // postListAdapter.data = PostListDummy.PostList().//
        rv_postList.adapter = postListAdapter
        rv_postList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
    }
}
