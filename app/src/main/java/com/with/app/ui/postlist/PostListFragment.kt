package com.with.app.ui.postlist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.with.app.R
import com.with.app.data.PickerDTO
import com.with.app.data.PostItem
import com.with.app.ui.posting.PostingActivity
import com.with.app.ui.postlist.recylcerview.PostListAdapter
import com.with.app.util.PrefManager
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.date_picker.view.*
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.fragment_post_list.view.*
import org.koin.android.ext.android.inject


class PostListFragment : Fragment() , SwipeRefreshLayout.OnRefreshListener{

    private val prefManager : PrefManager by inject()

    private var startDate : String = prefManager.startDate ?: "2019.12.25"
    private var endDate : String = prefManager.endDate ?: "2019.12.25"
    private lateinit var rvPostList : RecyclerView
    private lateinit var postListAdapter : PostListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        GetPostListData(view)
        view.btn_posting.setOnClickListener {
            val intent = Intent(context, PostingActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //datePicker 시작
        view.txt_datePicker.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.date_picker, null)

            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .show()

            dialogView.apply {
                // split한 데이터를 datepicker에 설정
                start_datepicker.setDate(startDate.splitDate())
                end_datepicker.setDate(endDate.splitDate())

                btn_close.setOnClickListener {
                    dialog.dismiss()
                }
                btn_save.setOnClickListener{
                    // SAVE와 동시에 LOAD시킴
                    this@PostListFragment.txt_datePicker.text = "${start_datepicker.saveLoad(1)} | ${end_datepicker.saveLoad(2)}"
                    dialog.dismiss()
                }
            }
        }
    }



    override fun onRefresh() {
        //mSwipeRefreshLayout.setRefreshing(false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edt_search.setOnClickListener {
            val intent = Intent(activity, ChangeRegionActivity::class.java)

            startActivity(intent)
        }
        swipe.setOnRefreshListener(this)

    }

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

    private fun DatePicker.saveLoad(mode : Int) : String{
        return when (mode) {
            1 -> {
                prefManager.startDate = "${this.year}.${this.month + 1}.${this.dayOfMonth}"
                return "${this.year % 100}.${this.month + 1}.${this.dayOfMonth}"
            }
            2 -> {
                prefManager.endDate = "${this.year}.${this.month + 1}.${this.dayOfMonth}"
                return "${this.year % 100}.${this.month + 1}.${this.dayOfMonth}"
            }
            else ->  ""
        }
    }

    private fun String.splitDate() : PickerDTO {
        val data = this.split(".")
        Log.e("DKDKDK", data.toString())
        val dto = PickerDTO(data[0].toInt(), data[1].toInt(), data[2].toInt())
        return dto
    }

    private fun DatePicker.setDate(data : PickerDTO) {
        this.updateDate(data.year, data.month-1, data.day)
    }

}



