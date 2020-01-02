package com.with.app.ui.detailpost

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.with.app.R
import com.with.app.data.remote.RequestChatOpenData
import com.with.app.manage.RecentViewsHelper
import com.with.app.manage.RequestManager
import com.with.app.ui.chatroom.ChatRoomActivity
import com.with.app.ui.posting.PostingActivity
import com.with.app.util.safeEnqueue
import com.with.app.util.toast
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_detail_post.img_profile
import kotlinx.android.synthetic.main.activity_detail_post.txt_date
import kotlinx.android.synthetic.main.activity_detail_post.txt_region
import kotlinx.android.synthetic.main.activity_detail_post.txt_title
import org.koin.android.ext.android.inject

class DetailPostActivity : AppCompatActivity(){

    private val requestManager : RequestManager by inject()
    private lateinit var recentViewsHelper : RecentViewsHelper
    private var myIdx = requestManager.authManager.idx
    private var userIdx = 0
    private var filter = -1
    private var boardIdx = 0
    private var badge = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)
        recentViewsHelper = RecentViewsHelper(applicationContext)
        boardIdx = intent.getIntExtra("boardIdx",0)
        getData()
        btn_edit.setOnClickListener {
            val intent = Intent(this,PostingActivity::class.java)
            intent.putExtra("title",txt_title.text)
            intent.putExtra("regionCode",txt_region.text)
            intent.putExtra("content",txt_content.text)
            intent.putExtra("date",txt_date.text)
            intent.putExtra("filter",filter)
            intent.putExtra("mode",1)
            intent.putExtra("boardIdx",boardIdx)
            startActivityForResult(intent, REQUESTCODE)
        }

        //게시글쓴 idx값과 접속한 idx값이 다르면 채팅버튼 보임, 마감이면 회색처리. 채팅버튼 비활성화
    }

    private fun getData(){
        requestManager.requestDetailBoard(boardIdx)
            .safeEnqueue (
                onSuccess = {
                    if (it.success) {
                        val response = it.data
                        txt_title.text = response.title
                        txt_region.text = response.regionName
                        txt_content.text = response.content
                        txt_date.text = response.startDate+" ~ "+response.endDate
                        filter = response.filter
                        if(filter == 1) txt_filter.text = "적용"
                        else txt_filter.text = "미적용"
                        userIdx = response.userIdx
                        txt_name.text = response.name

                        badge = response.badge
                        if(badge == 0) iv_like_level.setImageResource(R.drawable.like_level0)
                        else if(badge == 1) iv_like_level.setImageResource(R.drawable.like_level1)
                        else if(badge == 2) iv_like_level.setImageResource(R.drawable.like_level2)
                        else iv_like_level.setImageResource(R.drawable.like_level3)


                        if(it.data.gender == 1) txt_age_gender.text = response.birth.toString()+"살 남자"
                        else txt_age_gender.text = response.birth.toString()+"살 여자"
                        Glide.with(applicationContext)
                            .load(response.userImg)
                            .into(img_profile)

                        //게시글쓴 idx값과 접속한 idx값이 같으면 마감버튼, 수정버튼 보임/채팅버튼 사라짐
                        if (myIdx == userIdx) {
                            fab.visibility = View.GONE
                            btn_edit.visibility = View.VISIBLE
                            btn_dealine.visibility = View.VISIBLE
                        }
                        else{
                            recentViewsHelper.insertView(response.boardIdx)
                            fab.visibility = View.VISIBLE
                            btn_edit.visibility = View.GONE
                            btn_dealine.visibility = View.GONE

                        }

                        fab.setOnClickListener {
                            requestManager.requestChatOpen(
                                RequestChatOpenData(response.userIdx, response.boardIdx, "${response.boardIdx}_${response.userIdx}_${myIdx}")
                            ).safeEnqueue(
                                onSuccess = {
                                    if (it.success || it.message == "이미 채팅방이 존재합니다") {
                                        val intent = Intent(this, ChatRoomActivity::class.java)
                                        intent.putExtra("mode", POSTINGTOCHAT)
                                        intent.putExtra("boardIdx", response.boardIdx)
                                        intent.putExtra("writeUserIdx", response.userIdx.toString())
                                        intent.putExtra("regionName", response.regionName)
                                        intent.putExtra("startDate", response.startDate)
                                        intent.putExtra("endDate", response.endDate)
                                        intent.putExtra("title", response.title)
                                        intent.putExtra("userImg", response.userImg)
                                        intent.putExtra("name", response.name)
                                        intent.putExtra("senderUserIdx", myIdx.toString())
                                        intent.putExtra("withFlag", response.withFlag)
                                        startActivity(intent)
                                    } else {
                                        toast("네트워크 통신 오류")
                                    }
                                })
                        }
                    }
                }
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//수정된 값 다시 받아올때
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK){
            boardIdx = data?.getIntExtra("boardIdx",0)!!
            getData()
        }
    }

    companion object {
        const val POSTINGTOCHAT = 150
        const val REQUESTCODE = 0
    }
}
