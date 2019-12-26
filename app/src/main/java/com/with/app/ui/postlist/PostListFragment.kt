package com.with.app.ui.postlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.with.app.R
import com.with.app.data.PostItem
import com.with.app.ui.detailpost.DetailPostActivity
import com.with.app.ui.posting.PostingActivity
import com.with.app.ui.postlist.recylcerview.PostListAdapter
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.fragment_post_list.view.*


class PostListFragment : Fragment() , SwipeRefreshLayout.OnRefreshListener{

    private lateinit var rvPostList : RecyclerView
    private lateinit var postListAdapter : PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        /*view.txt_datePicker.setOnClickListener {
            ShowDatePicker(view)
        }//맞나?.*/

        GetPostListData(view)
        view.btn_posting.setOnClickListener {
            val intent = Intent(context, PostingActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onRefresh() {
        //mSwipeRefreshLayout.setRefreshing(false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe.setOnRefreshListener(this)
    }

/*    private fun ShowDatePicker(v : View) {
        //visiblity 속성중요
        val today = Calendar.getInstance()
        date_picker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ){ view, year, month, day ->
                val month = month + 1
                txt_datePicker.text = "$year.$month.$day | $year.$month.$day"
            }
    }*/

    private fun GetPostListData(v : View) {

        rvPostList = v.findViewById(R.id.rv_postList)
        postListAdapter =
            PostListAdapter(context!!)

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

