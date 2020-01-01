package com.with.app.ui.posting

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.with.app.R
import com.with.app.data.remote.RequestBoardData
import com.with.app.manage.RequestManager
import com.with.app.ui.detailpost.DetailPostActivity
import com.with.app.ui.home.HomeFragment
import com.with.app.ui.region.ChangeRegionActivity
import com.with.app.util.safeEnqueue
import com.with.app.util.toast
import kotlinx.android.synthetic.main.activity_posting.*
import kotlinx.android.synthetic.main.activity_posting.switch_filter
import kotlinx.android.synthetic.main.date_picker.view.*
import kotlinx.android.synthetic.main.fragment_post_list.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat

class PostingActivity : AppCompatActivity() {

    private var isSwitchChecked = -1
    private val requestManager : RequestManager by inject()
    private var boardIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)

        //게시글 수정
        if(intent.getIntExtra("mode",0)==1) {
            boardIdx = intent.getIntExtra("boardIdx", 0)
            //수정에서 넘어왔을 때 게시글 수정 텍스트 변경, 삭제 버튼
            edt_title.setText(intent.getStringExtra("title"))
            edt_region.setTextColor(Color.BLACK)
            edt_region.setText(intent.getStringExtra("regionCode"))
            edt_content.setText(intent.getStringExtra("content"))
            edt_date.setText(intent.getStringExtra("date"))
            edt_date.setTextColor(Color.BLACK)
            isSwitchChecked = intent.getIntExtra("filter", -1)//동성필터 여부 받아오기
            if(isSwitchChecked == 1){
                switch_filter.isChecked = false
                switch_filter.toggle()
            }

            btn_delete.visibility = View.VISIBLE
            txt_category.text = "게시글 수정"
            btn_save.setOnClickListener {
                var regionCode = edt_region.text.toString()
                var title = edt_title.text.toString()
                var content = edt_content.text.toString()
                var startDate = edt_date.text.split(" ~ ")[0]
                var endDate = edt_date.text.split(" ~ ")[1]
                var filter :Int
                if(switch_filter.isChecked){
                    filter = 1
                }
                else{
                    filter = -1
                }
                requestManager.requestBoardEdit(boardIdx,RequestBoardData(regionCode,title,content,startDate,endDate,filter))
                    .safeEnqueue (
                        onSuccess = {
                            val intent = Intent(this,DetailPostActivity::class.java)
                            //setResult(intent)
                        },
                        onError = {
                            Log.e("error", it.toString())
                        }
                    )

            }//서버에게 수정저장 요청후 상세 게시글 액티비티로 전환하면서 boardIdx넘김
        }

        else {//게시글 작성
            btn_delete.visibility = View.GONE
            btn_save.setOnClickListener {
                var regionCode = edt_region.text.toString()
                var title = edt_title.text.toString()
                var content = edt_content.text.toString()
                var startDate = edt_date.text.split(" ~ ")[0]
                var endDate = edt_date.text.split(" ~ ")[1]
                var filter :Int
                if(switch_filter.isChecked){
                    filter = 1
                }
                else{
                    filter = -1
                }
                requestManager.requestBoardWrite(RequestBoardData(regionCode,title,content,startDate,endDate,filter))
                    .safeEnqueue (
                        onSuccess = {
                            val intent = Intent(this,DetailPostActivity::class.java)
                            intent.putExtra("boardIdx",boardIdx)
                            startActivity(intent)
                        },
                        onError = {
                            Log.e("error", it.toString())
                        }
                    )
            }//서버에게 저장 요청
        }

        edt_region.setOnClickListener {
            val intent = Intent(this, ChangeRegionActivity::class.java)
            startActivityForResult(intent, HomeFragment.REGIONCHANGE_REQCODE)
        }

        edt_date.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.date_picker, null)

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()

            dialogView.apply {
                btn_close.setOnClickListener {
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
                    edt_date.text = "${start_datepicker.year%100}.${start_datepicker.month+1}.${start_datepicker.dayOfMonth} " +
                            "~ ${end_datepicker.year%100}.${end_datepicker.month + 1}.${end_datepicker.dayOfMonth}"
                    edt_date.setTextColor(Color.BLACK)
                    dialog.cancel()
                }
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HomeFragment.REGIONCHANGE_REQCODE && resultCode == Activity.RESULT_OK) {
            txt_country.text = requestManager.regionManager.name
        }
    }
}
