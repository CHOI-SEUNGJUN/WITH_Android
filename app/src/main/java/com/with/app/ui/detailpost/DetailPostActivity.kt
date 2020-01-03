package com.with.app.ui.detailpost

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.with.app.R
import com.with.app.data.remote.RequestChatOpenData
import com.with.app.extension.*
import com.with.app.manage.RecentViewsHelper
import com.with.app.manage.RequestManager
import com.with.app.ui.chatroom.ChatRoomActivity
import com.with.app.ui.posting.PostingActivity
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_detail_post.img_profile
import kotlinx.android.synthetic.main.activity_detail_post.iv_like_level
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
    }

    private fun getData(){
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade)
        layout_post_detail.inVisible()

        Log.e("boardIdx", boardIdx.toString())
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
                        txt_state_message.text = response.intro

                        badge = response.badge
                        if(badge == 0) iv_like_level.setImageResource(R.drawable.like_level0)
                        else if(badge == 1) iv_like_level.setImageResource(R.drawable.like_level1)
                        else if(badge == 2) iv_like_level.setImageResource(R.drawable.like_level2)
                        else iv_like_level.setImageResource(R.drawable.like_level3)

                        if(it.data.gender == 1) txt_age_gender.text = response.birth.toString()+"살 남자"
                        else txt_age_gender.text = response.birth.toString()+"살 여자"

                        img_profile.load(applicationContext,response.userImg)
                        img_background.load(applicationContext,response.userBgImg)

                        if (myIdx == userIdx) {
                            fab.gone()
                            btn_edit.visible()
                            btn_dealine.visible()
                        }
                        else{
                            recentViewsHelper.insertView(response.boardIdx)
                            fab.visible()
                            btn_edit.gone()
                            btn_dealine.gone()
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
                        layout_post_detail.startAnimation(animation)
                        layout_post_detail.visible()
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
