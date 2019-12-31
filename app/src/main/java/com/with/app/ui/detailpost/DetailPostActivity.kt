package com.with.app.ui.detailpost

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.with.app.R
import com.with.app.manage.AuthManager
import com.with.app.ui.posting.PostingActivity
import kotlinx.android.synthetic.main.activity_detail_post.*
import org.koin.android.ext.android.inject

class DetailPostActivity : AppCompatActivity(){

    private val authManager : AuthManager by inject()
    private var myIdx = authManager.idx
    private var posterIdx = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)


        //게시글쓴 idx값과 접속한 idx값이 같으면
        if (myIdx == posterIdx) {

        }

        btn_edit.setOnClickListener {
            val intent = Intent(this,PostingActivity::class.java)
            intent.putExtra("title",txt_title.text)
            intent.putExtra("regionCode",edt_region.text)
            intent.putExtra("content",edt_content.text)
            intent.putExtra("date",edt_date.text)
            intent.putExtra("filter",-1)//동성필터 여부 받아오기
            intent.putExtra("mode",1)
            startActivityForResult(intent,-1)
        }

        //게시글쓴 idx값과 접속한 idx값이 다르면 채팅버튼 보임, 마감이면 회색처리. 채팅버튼 비활성화
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//수정된 값 다시 받아올때
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){

            }
        }
    }
}
