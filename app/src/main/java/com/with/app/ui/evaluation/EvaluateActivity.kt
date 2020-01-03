package com.with.app.ui.evaluation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.with.app.R
import com.with.app.data.remote.ResponseChatListArrayData
import com.with.app.data.remote.RoomIdData
import com.with.app.extension.*
import com.with.app.manage.RequestManager
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.dialog_evaluation.*
import org.koin.android.ext.android.inject

class EvaluateActivity : AppCompatActivity() {

    private val requestManager : RequestManager by inject()
    private val evalData : MutableList<ResponseChatListArrayData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)
        init()
        setData()
    }

    private fun init() {
        btn_close.setOnClickListener {
            showEvalPopup()
        }
    }

    private fun setData() {
        showLoading(loading)
        requestManager.requestChatList().safeEnqueue(
            onSuccess = {
                if (it.success) {
                    val data = it.data
                    for (item in data) if (item.evalFlag == 2) evalData.add(item)
                    setListener()
                    hideLoading(loading)
                }
            }
        )
    }

    private fun setListener() {

        evalContainer.visibility = View.GONE

        var animationEval = AnimationUtils.loadAnimation(this, R.anim.fade)
        evalContainer.visibility = View.VISIBLE
        evalContainer.startAnimation(animationEval)

        val totalCount = evalData.size
        var name = evalData[0].name
        container.load(this, evalData[0].regionImgE, loadingContainer, evalContainer)
        tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
        tv_elevation_count_total.text = "$totalCount"

        if(totalCount == 1) {
            btn_left.gone()
            btn_right.gone()
        } else {
            btn_left.gone()
            btn_right.visible()
        }

        btn_right.setOnClickListener {
            var animationEvalRight = AnimationUtils.loadAnimation(this, R.anim.fade)

            var evalCount = tv_elevation_count.text.toString().toInt()
            var dataPosition = evalCount - 1

            requestManager.requestNoEvaluation(RoomIdData(evalData[dataPosition].roomId))
                .safeEnqueue(
                    onSuccess = {
                        evalContainer.visibility = View.GONE

                        var nextMate = evalCount
                        var count = evalCount + 1
                        name = evalData[nextMate].name
                        var currentCount = count.toString()
                        tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                        btn_elevation_top.text = "즐거웠어요"
                        btn_elevation_bottom.text = "별로였어요"
                        tv_elevation_count.text = currentCount
                        container.load(this, evalData[nextMate].regionImgE, loadingContainer, evalContainer)

                        if (count > 1) btn_left.visible()
                        if (count == totalCount) btn_right.gone()

                        evalContainer.visibility = View.VISIBLE
                        evalContainer.startAnimation(animationEvalRight)
                    },
                    onError = {
                        toast("네트워크 통신 오류")
                    },
                    onFailure = {
                        toast("네트워크 통신 오류")
                    }
                )
        }

        btn_left.setOnClickListener {
            var evalCount = tv_elevation_count.text.toString().toInt()
            var dataPosition = evalCount - 1
            btn_right.visible()

            var animationEvalLeft = AnimationUtils.loadAnimation(this, R.anim.fade)

            requestManager.requestNoEvaluation(RoomIdData(evalData[dataPosition].roomId))
                .safeEnqueue(
                    onSuccess = {
                        evalContainer.visibility = View.GONE

                        var prevMate = evalCount - 2
                        var count = evalCount - 1
                        name = evalData[prevMate].name
                        var currentCount = count.toString()
                        tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                        btn_elevation_top.text = "즐거웠어요"
                        btn_elevation_bottom.text = "별로였어요"
                        tv_elevation_count.text = currentCount
                        container.load(this, evalData[0].regionImgE, loadingContainer, evalContainer)

                        if (count > 1) btn_left.visible()
                        if (count == 1) btn_left.gone()

                        evalContainer.visibility = View.VISIBLE
                        evalContainer.startAnimation(animationEvalLeft)
                    },
                    onError = {
                        toast("네트워크 통신 오류")
                    },
                    onFailure = {
                        toast("네트워크 통신 오류")
                    }
                )
        }

        btn_elevation_bottom.setOnTouchListener { v, event ->
            var evalCount = tv_elevation_count.text.toString().toInt()
            var bottomText = btn_elevation_bottom.text.toString()
            var dataPosition = evalCount - 1

            var end = false
            when (event?.action) {

                MotionEvent.ACTION_DOWN -> {
                    btn_elevation_bottom.setBackgroundColor(Color.parseColor("#311a80"))
                }

                MotionEvent.ACTION_UP -> {
                    var animationEvalBottom = AnimationUtils.loadAnimation(this, R.anim.fade)

                    btn_elevation_bottom.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                    if (bottomText == "위드하기") finish()
                    if (evalCount == totalCount) {
                        requestManager.requestPutDisLike(RoomIdData(evalData[dataPosition].roomId))
                            .safeEnqueue(
                                onSuccess = {
                                    evalContainer.visibility = View.GONE

                                    tv_elevation_intro.text = "감사합니다\n앞으로도 W!TH해요 :)"
                                    btn_elevation_bottom.text = "위드하기"
                                    btn_left.gone()
                                    btn_right.gone()
                                    btn_elevation_top.gone()
                                    // container.setImageResource(R.drawable.evaluationimg)

                                    evalContainer.visibility = View.VISIBLE
                                    evalContainer.startAnimation(animationEvalBottom)

                                },
                                onFailure = {
                                    toast("네트워크 통신 오류")
                                },
                                onError = {
                                    toast("네트워크 통신 오류")
                                }
                            )

                    } else {
                        requestManager.requestPutDisLike(RoomIdData(evalData[dataPosition].roomId))
                            .safeEnqueue(
                                onSuccess = {
                                    evalContainer.visibility = View.GONE

                                    name = evalData[evalCount].name
                                    var count = evalCount + 1
                                    tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                                    btn_elevation_top.text = "즐거웠어요"
                                    btn_elevation_bottom.text = "별로였어요"
                                    tv_elevation_count.text = count.toString()
                                    container.load(this, evalData[0].regionImgE, loadingContainer, evalContainer)
                                    if (count > 1) btn_left.visible()
                                    if (count == totalCount) btn_right.gone()
                                    evalContainer.visibility = View.VISIBLE
                                    evalContainer.startAnimation(animationEvalBottom)

                                },
                                onFailure = {
                                    toast("네트워크 통신 오류")
                                    Log.e("onFailure", it.toString())
                                },
                                onError = {
                                    toast("네트워크 통신 오류")
                                    Log.e("onError", it.toString())
                                }
                            )
                    }
                }
            }
            false
        }

        btn_elevation_top.setOnTouchListener { v, event ->
            var evalCount = tv_elevation_count.text.toString().toInt()
            var dataPosition = evalCount - 1
            var top_text = btn_elevation_top.text.toString()

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn_elevation_top.setBackgroundColor(Color.parseColor("#311a80"))
                }

                MotionEvent.ACTION_UP -> {
                    var animationEvalTop = AnimationUtils.loadAnimation(this, R.anim.fade)

                    btn_elevation_top.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                    if (evalCount == totalCount) {
                        requestManager.requestPutLike(RoomIdData(evalData[dataPosition].roomId))
                            .safeEnqueue(
                                onSuccess = {
                                    evalContainer.visibility = View.GONE

                                    tv_elevation_intro.text = "감사합니다\n앞으로도 W!TH해요 :)"
                                    btn_elevation_bottom.text = "위드하기"
                                    btn_elevation_top.gone()
                                    btn_close.gone()
                                    img_person.gone()
                                    tv_elevation_count.gone()
                                    tv_elevation_div.gone()
                                    tv_elevation_count_total.gone()
                                    btn_left.gone()
                                    btn_right.gone()

                                    evalContainer.visibility = View.VISIBLE
                                    evalContainer.startAnimation(animationEvalTop)
                                },
                                onFailure = {
                                    toast("네트워크 통신 오류")
                                },
                                onError = {
                                    toast("네트워크 통신 오류")
                                }
                            )
                    } else {
                        requestManager.requestPutLike(RoomIdData(evalData[dataPosition].roomId))
                            .safeEnqueue(
                                onSuccess = {
                                    evalContainer.visibility = View.GONE

                                    name = evalData[evalCount].name
                                    var count = evalCount + 1
                                    var count_string = count.toString()

                                    tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                                    btn_elevation_top.text = "즐거웠어요"
                                    btn_elevation_bottom.text = "별로였어요"
                                    tv_elevation_count.text = count_string
                                    btn_close.visible()
                                    container.load(this, evalData[0].regionImgE, loadingContainer, evalContainer)

                                    if (count > 1) btn_left.visible()
                                    if (count == totalCount) btn_right.gone()
                                    evalContainer.visibility = View.VISIBLE
                                    evalContainer.startAnimation(animationEvalTop)
                                },
                                onFailure = {
                                    toast("네트워크 통신 오류")
                                },
                                onError = {
                                    toast("네트워크 통신 오류")
                                }
                            )
                    }
                }
            }
            false
        }
    }

    private fun showEvalPopup() {
        MaterialDialog(this).show {
            customView(R.layout.dialog_evaluation)
            btn_eval_ok.setOnClickListener {
                for(item in evalData) {
                    requestManager.requestNoEvaluation(RoomIdData(item.roomId))
                        .safeEnqueue()
                }
                finish()
            }
            btn_eval_no.setOnClickListener { dismiss() }
        }
    }
}