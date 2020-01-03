package com.with.app.ui.posting

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.with.app.data.remote.RequestBoardData
import com.with.app.manage.RequestManager
import com.with.app.ui.detailpost.DetailPostActivity
import com.with.app.ui.home.HomeFragment
import com.with.app.ui.region.ChangeRegionActivity
import com.with.app.extension.safeEnqueue
import kotlinx.android.synthetic.main.activity_posting.*
import kotlinx.android.synthetic.main.activity_posting.btn_save
import kotlinx.android.synthetic.main.activity_posting.switch_filter
import kotlinx.android.synthetic.main.date_picker.view.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.with.app.R
import com.with.app.manage.PrefManager
import com.with.app.extension.gone
import com.with.app.extension.toast
import com.with.app.extension.visible
import kotlinx.android.synthetic.main.dialog_posting.*

class PostingActivity : AppCompatActivity() {

    private var isSwitchChecked = 0
    private val requestManager : RequestManager by inject()
    private val prefManager : PrefManager by inject()
    private var boardIdx = 0
    private var mode = 0
    private var regionCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)

        // 게시글 수정
        if(intent.getIntExtra("mode",0)==1) {
            boardIdx = intent.getIntExtra("boardIdx", 0)
            edt_title.setText(intent.getStringExtra("title"))
            edt_region.toBlack()
            edt_region.setText(intent.getStringExtra("regionCode"))
            edt_content.setText(intent.getStringExtra("content"))
            edt_date.setText(intent.getStringExtra("date"))
            edt_date.toBlack()
            isSwitchChecked = intent.getIntExtra("filter", 0)//동성필터 여부 받아오기
            if(isSwitchChecked == 1){
                switch_filter.isChecked = false
                switch_filter.toggle()
            }

            btn_delete.visible()
            txt_category.text = "게시글 수정"
            mode = 1
        } else {//게시글 작성
            edt_region.setText(requestManager.regionManager.name)
            edt_region.toBlack()
            if(prefManager.startDate != "0") {
                edt_date.setText(
                    "${prefManager.startDate.substring(2)} ~ ${prefManager.endDate.substring(2)}"
                )
                edt_date.toBlack()
            }
            btn_delete.gone()
            mode = 0
        }

        btn_save.setOnClickListener {
            regionCode = requestManager.regionManager.code

            if(regionCode.isEmpty()||edt_title.text.isEmpty()||edt_content.text.isEmpty()||edt_date.text.isEmpty()){
                toast("내용을 모두 입력해주세요")
                return@setOnClickListener
            }

            var title = edt_title.text.toString()
            var content = edt_content.text.toString()
            var startDate = edt_date.text.split(" ~ ")[0]
            var endDate = edt_date.text.split(" ~ ")[1]
            var filter: Int
            if (switch_filter.isChecked) filter = 1 else filter = -1
            if (mode == 1) {
                requestManager.requestBoardEdit(boardIdx, RequestBoardData(regionCode, title, content, startDate, endDate, filter))
                    .safeEnqueue(
                        onSuccess = {
                            val intent = Intent()
                            intent.putExtra("boardIdx", boardIdx)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    )
            }
            else if(mode == 0) {
                Log.e("test", "$regionCode,$title,$content,$startDate,$endDate,$filter")
                requestManager.requestBoardWrite(RequestBoardData(regionCode,title,content,startDate,endDate,filter))
                    .safeEnqueue (
                        onSuccess = {
                            Log.e("success", it.message)
                            val intent = Intent(this,DetailPostActivity::class.java)
                            intent.putExtra("boardIdx",it.data[0].boardIdx)
                            startActivity(intent)
                            finish()
                        }
                    )
            }
        }

        edt_region.setOnClickListener {
            val intent = Intent(this, ChangeRegionActivity::class.java)
            startActivityForResult(intent, HomeFragment.REGIONCHANGE_REQCODE)
        }

        edt_date.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.date_picker, null)
            dialogView.btn_select_all.gone()

            //removeView(dialogView.start_datepicker)
            //removeView(dialogView.end_datepicker)

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()

            dialog.window?.setLayout(290*4, 500*4)

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
                        toast("마감일이 시작일보다 늦어야합니다.")
                        return@setOnClickListener
                    }
                    edt_date.text = "${start_datepicker.year%100}.${start_datepicker.month+1}.${start_datepicker.dayOfMonth} " +
                            "~ ${end_datepicker.year%100}.${end_datepicker.month + 1}.${end_datepicker.dayOfMonth}"
                    edt_date.toBlack()
                    dialog.cancel()
                }
            }
        }
    }

    override fun onBackPressed() {
        showSettingPopup()
    }

    private fun showSettingPopup() {
        MaterialDialog(this).show {
            customView(R.layout.dialog_posting)
            btn_dialog_mypage_ok.setOnClickListener { finish() }
            btn_dialog_mypage_cancle.setOnClickListener { dismiss() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HomeFragment.REGIONCHANGE_REQCODE && resultCode == Activity.RESULT_OK) {
            edt_region.text = requestManager.regionManager.name
            edt_region.toBlack()
        }
    }

    private fun TextView.toBlack() {
        this.setTextColor(Color.BLACK)
    }

    private fun removeView(picker : DatePicker) {
        if (picker.parent != null) (picker.parent as ViewGroup).removeView(picker)
    }

}
