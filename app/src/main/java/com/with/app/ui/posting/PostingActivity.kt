package com.with.app.ui.posting

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.with.app.R
import com.with.app.ui.chatlist.ChatListFragment
import com.with.app.ui.postlist.PostListFragment
import com.with.app.util.toast
import kotlinx.android.synthetic.main.activity_posting.*
import kotlinx.android.synthetic.main.activity_posting.btn_save
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.date_picker.view.*
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.item_post_bulletin.*
import java.text.SimpleDateFormat

class PostingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)

        //수정에서 넘어왔을 때 게시글 수정 택스트 변경, 삭제 버튼

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
                            "| \"${end_datepicker.year%100}.${end_datepicker.month + 1}.${end_datepicker.dayOfMonth}\""
                    dialog.cancel()
                }
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
