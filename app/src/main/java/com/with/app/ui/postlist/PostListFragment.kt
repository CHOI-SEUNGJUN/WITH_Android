package com.with.app.ui.postlist

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.data.PickerDTO
import com.with.app.ui.posting.PostingActivity
import com.with.app.ui.postlist.recylcerview.PostListAdapter
import com.with.app.manage.PrefManager
import com.with.app.manage.RequestManager
import com.with.app.ui.home.HomeFragment
import com.with.app.ui.recent.RecentSearchesActivity
import com.with.app.ui.region.ChangeRegionActivity
import com.with.app.util.gone
import com.with.app.util.safeEnqueue
import com.with.app.util.toast
import com.with.app.util.visible
import kotlinx.android.synthetic.main.date_picker.view.*
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.fragment_post_list.view.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat

class PostListFragment : Fragment()
{
    private val prefManager : PrefManager by inject()
    private val requestManager : RequestManager by inject()
    private var filter = -1
    private var keyword = ""

    private var startDate : String = prefManager.startDate
    private var endDate : String = prefManager.endDate
    private lateinit var rvPostList : RecyclerView
    private lateinit var postListAdapter : PostListAdapter
    private lateinit var edt_search : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        view.btn_posting.setOnClickListener {
            val intent = Intent(context, PostingActivity::class.java)
            startActivity(intent)
        }

        edt_search = view.findViewById(R.id.edt_search)
        GetPostListData(view)

        if (prefManager.startDate != prefManager.endDate) {
            val splitStart = prefManager.startDate.splitDate()
            val splitEnd = prefManager.endDate.splitDate()
            view.txt_datePicker.text = "${splitStart.year%100}.${splitStart.month}.${splitStart.day} ~ ${splitEnd.year%100}.${splitEnd.month}.${splitEnd.day}"
        } else if (prefManager.startDate == "0") {
            view.txt_datePicker.text = "날짜"
        } else {
            val splitStart = prefManager.startDate.splitDate()
            view.txt_datePicker.text = "${splitStart.year%100}.${splitStart.month}.${splitStart.day}"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_country.text = requestManager.regionManager.name

        swipe.setOnRefreshListener {
            getDataWhenClick()
            swipe.isRefreshing = false
        }

        //지역선택
        txt_country.setOnClickListener {
            val intent = Intent(context, ChangeRegionActivity::class.java)
            startActivityForResult(intent, HomeFragment.REGIONCHANGE_REQCODE)
            //지역선택에서 값 받아서 txt_country 설정해야함
        }

        //검색
        edt_search.setOnClickListener {
            val intent = Intent(activity, RecentSearchesActivity::class.java)
            intent.putExtra("text",edt_search.text)
            startActivityForResult(intent, REQUESTCODE)
            getDataWhenClick()
        }

        //동성필터
        view.switch_filter.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                filter = 1
                getDataWhenClick()
            }
            else {
                filter = 0
                getDataWhenClick()
            }
        }

        //datePicker
        txt_datePicker.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.date_picker, null)

            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .show()

            dialogView.apply {
                // split한 데이터를 datepicker에 설정
                when {
                    "날짜" == this@PostListFragment.txt_datePicker.text -> {
                    }
                    else -> {
                        start_datepicker.setDate(prefManager.startDate.splitDate())
                        end_datepicker.setDate(prefManager.endDate.splitDate())
                    }
                }

                btn_close.setOnClickListener {
                    dialog.cancel()
                }
                btn_select_all.setOnClickListener{
                    //날짜 전체 선택
                    this@PostListFragment.txt_datePicker.text = "${start_datepicker.saveLoad(3)}"
                    end_datepicker.saveLoad(3)
                    getDataWhenClick()
                    dialog.cancel()
                }
                btn_save.setOnClickListener{
                    val tempStart = "${start_datepicker.year}.${start_datepicker.month+1}.${start_datepicker.dayOfMonth}"
                    val tempEnd = "${end_datepicker.year}.${end_datepicker.month+1}.${end_datepicker.dayOfMonth}"
                    val pattern = SimpleDateFormat("yyyy.MM.dd")
                    val diffs = pattern.parse(tempEnd).compareTo(pattern.parse(tempStart))
                    if (diffs == -1) {
                        context.toast("마감일이 시작일보다 늦어야합니다.")
                        return@setOnClickListener
                    }
                    else if (diffs == 0) {
                        this@PostListFragment.txt_datePicker.text = "${start_datepicker.saveLoad(1)}"
                        end_datepicker.saveLoad(2)
                    }
                    else {
                        this@PostListFragment.txt_datePicker.text = "${start_datepicker.saveLoad(1)} ~ ${end_datepicker.saveLoad(2)}"
                    }
                    // SAVE와 동시에 LOAD시킴
                    prefManager.startDate = "${start_datepicker.year}.${start_datepicker.month+1}.${start_datepicker.dayOfMonth}"
                    prefManager.endDate = "${end_datepicker.year}.${end_datepicker.month+1}.${end_datepicker.dayOfMonth}"
                    getDataWhenClick()
                    dialog.cancel()
                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK) {
            edt_search.text = data?.getStringExtra("keyword")
            getDataWhenClick()
        } else if (requestCode == HomeFragment.REGIONCHANGE_REQCODE && resultCode == Activity.RESULT_OK) {
            txt_country.text = requestManager.regionManager.name
            // TODO : 서버통신하기
            getDataWhenClick()
        }
    }

/*    override fun onRefresh() {
        //새로고침 구현
    }*/

    private fun GetPostListData(v : View) {
        rvPostList = v.findViewById(R.id.rv_postList)
        postListAdapter =
            PostListAdapter(context!!)

        rvPostList.adapter = postListAdapter
        rvPostList.layoutManager = LinearLayoutManager(context)

        getDataWhenClick()

    }

    private fun getDataWhenClick() {
        val regionCode = requestManager.regionManager.code
        startDate = prefManager.startDate
        endDate = prefManager.endDate

        if(edt_search.text.isEmpty()){
            keyword = "0"
        }
        else{
            keyword = edt_search.text.toString()
        }
        postListAdapter.data = listOf()
        postListAdapter.notifyDataSetChanged()
        requestManager.requestSearchBoard(regionCode,startDate,endDate,keyword,filter)
            .safeEnqueue (
                onSuccess = {
                    if(it.data.isEmpty()) {
                        txt_blank.visible()
                        textView.gone()
                        textView4.gone()
                        switch_filter.gone()
                        rv_postList.gone()
                    } else {
                        txt_blank.gone()
                        textView.visible()
                        textView4.visible()
                        switch_filter.visible()
                        rv_postList.visible()
                        postListAdapter.data = it.data
                    }
                    postListAdapter.notifyDataSetChanged()
                },
                onError = {
                    Log.e("error", it.toString())
                },
                onFailure = {
                    Log.e("failure", it.message())
                }
            )
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
            3 -> {
                prefManager.startDate = "0"
                prefManager.endDate = "0"
                return "날짜"
            }
            else ->  ""
        }
    }

    private fun String.splitDate() : PickerDTO {
        val data = this.split(".")
        val dto = PickerDTO(data[0].toInt(), data[1].toInt(), data[2].toInt())
        return dto
    }

    private fun DatePicker.setDate(data : PickerDTO) {
        this.updateDate(data.year, data.month-1, data.day)
    }

    companion object {
        private const val REQUESTCODE = 0
    }

}



