package com.with.app.ui.detailpost

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.ui.posting.PostingActivity
import com.with.app.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_detail_post.img_profile
import kotlinx.android.synthetic.main.activity_detail_post.txt_date
import kotlinx.android.synthetic.main.activity_detail_post.txt_region
import kotlinx.android.synthetic.main.activity_detail_post.txt_title
import kotlinx.android.synthetic.main.item_post_bulletin.*
import org.koin.android.ext.android.inject

class DetailPostActivity : AppCompatActivity(){

    private val requestManager : RequestManager by inject()
    private var myIdx = requestManager.authManager.idx
    private var userIdx = 10
    private var filter = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)

        requestManager.requestDetailBoard(intent.getIntExtra("boardIdx",0))
            .safeEnqueue (
                onSuccess = {
                    txt_title.text = it.data.title
                    txt_region.text = it.data.regionName
                    txt_content.text = it.data.content
                    txt_date.text = it.data.startDate+" ~ "+it.data.endDate
                    filter = it.data.filter
                    if(filter == 1){
                        txt_filter.text = "적용"
                    }
                    else{
                        txt_filter.text = "미적용"
                    }
                    userIdx = it.data.userIdx
                    txt_name.text = it.data.name
                    if(it.data.gender == 1){
                        txt_age_gender.text = it.data.birth.toString()+"살 남자"
                    }
                    else{
                        txt_age_gender.text = it.data.birth.toString()+"살 여자"
                    }
                    Glide.with(applicationContext)
                        .load(it.data.userImg)
                        .into(img_profile)

                },
                onError = {
                    Log.e("error", it.toString())
                }
            )

        //게시글쓴 idx값과 접속한 idx값이 같으면 마감버튼, 수정버튼 보임/채팅버튼 사라짐
        if (myIdx == userIdx) {
            fab.visibility = View.GONE
            btn_edit.visibility = View.VISIBLE
            btn_dealine.visibility = View.VISIBLE
        }
        else{
            fab.visibility = View.GONE
            btn_edit.visibility = View.GONE
            btn_dealine.visibility = View.GONE
        }

        btn_edit.setOnClickListener {
            val intent = Intent(this,PostingActivity::class.java)
            intent.putExtra("title",txt_title.text)
            intent.putExtra("regionCode",txt_region.text)
            intent.putExtra("content",txt_content.text)
            intent.putExtra("date",txt_date.text)
            intent.putExtra("filter",filter)//동성필터 여부 받아온 값 넣기
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
