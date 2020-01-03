package com.with.app.ui.evaluation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.with.app.R
import com.with.app.data.remote.ResponseChatListArrayData
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

        //tv_elevation_count.setText("$eval_count")


    }

    private fun init() {
        btn_close.setOnClickListener {
            showEvalPopup()
        }
    }

    private fun setData() {
        requestManager.requestChatList().safeEnqueue(
            onSuccess = {
                if (it.success) {
                    val data = it.data
                    Log.e("data", data.toString())
                    for (item in data) {
                        if (item.evalFlag == 2) {
                            evalData.add(item)
                        }
                    }
                    setListener()
                }
            }
        )
    }

    private fun setListener() {
        val totalCount = evalData.size
        var name = evalData[0].name
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
            var evalCount = tv_elevation_count.text.toString().toInt()
            var dataPosition = evalCount - 1

            requestManager.requestNoEvaluation(evalData[dataPosition].roomId)
                .safeEnqueue(
                    onSuccess = {
                        var nextMate = evalCount
                        var count = evalCount + 1
                        name = evalData[nextMate].name
                        var currentCount = count.toString()
                        tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                        btn_elevation_top.text = "좋았어요"
                        btn_elevation_bottom.text = "별로였어요"
                        tv_elevation_count.text = currentCount
                        if (count > 1) {
                            btn_left.visibility = View.VISIBLE
                        }
                        if (count == totalCount) {
                            btn_right.visibility = View.GONE
                        }
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
            btn_right.visibility = View.VISIBLE

            requestManager.requestNoEvaluation(evalData[evalCount].roomId)
                .safeEnqueue(
                    onSuccess = {
                        var prevMate = evalCount - 2
                        var count = evalCount - 1
                        name = evalData[prevMate].name
                        var currentCount = count.toString()
                        tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                        btn_elevation_top.text = "좋았어요"
                        btn_elevation_bottom.text = "별로였어요"
                        tv_elevation_count.text = currentCount
                        if (count > 1) {
                            btn_left.visibility = View.VISIBLE
                        }
                        if (count == 1) {
                            btn_left.visibility = View.GONE
                        }
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

            var end = false
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn_elevation_bottom.setBackgroundColor(Color.parseColor("#311a80"))
                }

                MotionEvent.ACTION_UP -> {
                    btn_elevation_bottom.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                    if (bottomText == "위드하기") finish()
                    if (evalCount == totalCount) {
                        requestManager.requestPutDisLike(evalData[evalCount].roomId)
                            .safeEnqueue(
                                onSuccess = {
                                    tv_elevation_intro.text = "감사합니다\n앞으로도 W!TH해요 :)"
                                    btn_elevation_bottom.text = "위드하기"
                                    btn_left.visibility = View.GONE
                                    btn_right.visibility = View.GONE
                                    btn_elevation_top.visibility = View.GONE
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

                    } else {
                        Log.e("id", evalData[evalCount].roomId)
                        requestManager.requestPutDisLike(evalData[evalCount].roomId)
                            .safeEnqueue(
                                onSuccess = {
                                    name = evalData[evalCount].name
                                    var count = evalCount + 1
                                    tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                                    btn_elevation_top.text = "좋았어요"
                                    btn_elevation_bottom.text = "별로였어요"
                                    tv_elevation_count.text = count.toString()
                                    if (count > 1) {
                                        btn_left.visibility = View.VISIBLE
                                    }
                                    if (count == totalCount) {
                                        btn_right.visibility = View.GONE
                                    }
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

            var top_text = btn_elevation_top.text.toString()

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn_elevation_top.setBackgroundColor(Color.parseColor("#311a80"))
                }

                MotionEvent.ACTION_UP -> {
                    btn_elevation_top.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                    if (evalCount == totalCount) {
                        requestManager.requestPutLike(evalData[evalCount].roomId)
                            .safeEnqueue(
                                onSuccess = {
                                    tv_elevation_intro.text = "감사합니다\n앞으로도 W!TH해요 :)"
                                    btn_elevation_bottom.text = "위드하기"
                                    btn_elevation_top.visibility = View.GONE
                                    btn_close.visibility = View.GONE
                                    img_person.visibility = View.GONE
                                    tv_elevation_count.visibility = View.GONE
                                    tv_elevation_div.visibility = View.GONE
                                    tv_elevation_count_total.visibility = View.GONE
                                    btn_left.visibility = View.GONE
                                    btn_right.visibility = View.GONE
                                },
                                onFailure = {
                                    toast("네트워크 통신 오류")
                                },
                                onError = {
                                    toast("네트워크 통신 오류")
                                }
                            )


                    } else {
                        requestManager.requestPutLike(evalData[evalCount].roomId)
                            .safeEnqueue(
                                onSuccess = {
                                    name = evalData[evalCount].name
                                    var count = evalCount + 1
                                    var count_string = count.toString()

                                    tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                                    btn_elevation_top.setText("좋았어요")
                                    btn_elevation_bottom.setText("별로였어요")
                                    tv_elevation_count.setText(count_string)
                                    btn_close.visibility = View.VISIBLE
                                    if (count > 1) {
                                        btn_left.visibility = View.VISIBLE
                                    }
                                    if (count == totalCount) {
                                        btn_right.visibility = View.GONE
                                    }
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
                finish()
            }
            btn_eval_no.setOnClickListener {
                dismiss()
            }
        }
    }
}