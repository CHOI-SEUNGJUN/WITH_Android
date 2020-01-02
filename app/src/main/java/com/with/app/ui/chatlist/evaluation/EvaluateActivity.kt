package com.with.app.ui.chatlist.evaluation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.with.app.R
import com.with.app.util.toSpanned
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.dialog_evaluation.*

class EvaluateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)

        var eval_mate = ArrayList<String>()
        eval_mate.add("석영현")
        eval_mate.add("안현준")
        eval_mate.add("조현아")
        eval_mate.add("최승준")



        //tv_elevation_count.setText("$eval_count")

        val eval_count_total = eval_mate.size
        var name = eval_mate[0]
        tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
        tv_elevation_count_total.setText("$eval_count_total")

        img_close_button.setOnClickListener {
            showEvalPopup()
        }

        if (eval_count_total == 1) {
            btn_left.visibility = View.GONE
            btn_right.visibility = View.GONE
            btn_elevation_bottom.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var eval_count = tv_elevation_count.text.toString().toInt()
                    var bottom_text = btn_elevation_bottom.text.toString()

                    var end = false
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            btn_elevation_bottom.setBackgroundColor(Color.parseColor("#311a80"))
                            if (bottom_text == "위드하기") {

                                finish()
                            }

                        }

                        MotionEvent.ACTION_UP -> {
                            btn_elevation_bottom.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                            if (bottom_text == "위드하기") {
                            } else {
                                img_close_button.visibility = View.GONE
                                tv_elevation_intro.setText("감사합니다\n앞으로도 W!TH해요 :)")
                                btn_elevation_bottom.setText("위드하기")
                                btn_elevation_top.visibility = View.GONE
                            }
                        }
                    }
                    return false
                }
            })

            btn_elevation_top.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var top_text = btn_elevation_top.text.toString()

                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            btn_elevation_top.setBackgroundColor(Color.parseColor("#311a80"))
                        }

                        MotionEvent.ACTION_UP -> {
                            btn_elevation_top.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                            tv_elevation_intro.setText("감사합니다\n앞으로도 W!TH해요 :)")
                            btn_elevation_bottom.setText("위드하기")
                            btn_elevation_top.visibility = View.GONE
                            img_close_button.visibility = View.GONE
                        }
                    }
                    return false
                }
            })

        } else {
            btn_right.visibility = View.VISIBLE
            btn_right.setOnClickListener {
                var eval_count = tv_elevation_count.text.toString().toInt()

                Log.v("right button", "click")

                var next_mate = eval_count
                var count = eval_count + 1
                name = eval_mate[next_mate]
                var count_string = count.toString()
                tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                btn_elevation_top.setText("좋았어요")
                btn_elevation_bottom.setText("별로였어요")
                tv_elevation_count.setText(count_string)
                img_close_button.visibility = View.VISIBLE
                if(count > 1) {
                    btn_left.visibility = View.VISIBLE
                }
                if(count == eval_count_total) {
                    btn_right.visibility = View.GONE
                }
            }
            btn_left.setOnClickListener {
                var eval_count = tv_elevation_count.text.toString().toInt()
                btn_right.visibility = View.VISIBLE

                Log.v("left button", "click")

                var prev_mate = eval_count - 2
                var count = eval_count - 1
                name = eval_mate[prev_mate]
                var count_string = count.toString()
                tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                btn_elevation_top.setText("좋았어요")
                btn_elevation_bottom.setText("별로였어요")
                tv_elevation_count.setText(count_string)
                img_close_button.visibility = View.VISIBLE
                if(count > 1) {
                    btn_left.visibility = View.VISIBLE
                }
                if(count == 1) {
                    btn_left.visibility = View.GONE
                }
            }

            btn_elevation_bottom.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var eval_count = tv_elevation_count.text.toString().toInt()
                    var bottom_text = btn_elevation_bottom.text.toString()

                    var end = false
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            btn_elevation_bottom.setBackgroundColor(Color.parseColor("#311a80"))
                        }

                        MotionEvent.ACTION_UP -> {
                            btn_elevation_bottom.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                            if (eval_count == eval_count_total) {
                                tv_elevation_intro.setText("감사합니다\n앞으로도 W!TH해요 :)")
                                btn_elevation_bottom.setText("위드하기")
                                btn_left.visibility = View.GONE
                                btn_right.visibility = View.GONE
                                btn_elevation_top.visibility = View.GONE
                                img_close_button.visibility = View.GONE

                            } else {
                                name = eval_mate[eval_count]
                                var count = eval_count + 1
                                var count_string = count.toString()
                                tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                                btn_elevation_top.setText("좋았어요")
                                btn_elevation_bottom.setText("별로였어요")
                                tv_elevation_count.setText(count_string)
                                img_close_button.visibility = View.VISIBLE
                                if(count > 1) {
                                    btn_left.visibility = View.VISIBLE
                                }
                                if(count == eval_count_total) {
                                    btn_right.visibility = View.GONE
                                }
                            }
                        }
                    }
                    return false
                }
            })

            btn_elevation_top.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var eval_count = tv_elevation_count.text.toString().toInt()

                    var top_text = btn_elevation_top.text.toString()

                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            btn_elevation_top.setBackgroundColor(Color.parseColor("#311a80"))
                        }

                        MotionEvent.ACTION_UP -> {
                            btn_elevation_top.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                            if (eval_count == eval_count_total) {
                                tv_elevation_intro.text = "감사합니다\n앞으로도 W!TH해요 :)"
                                btn_elevation_bottom.setText("위드하기")
                                btn_elevation_top.visibility = View.GONE
                                img_close_button.visibility = View.GONE
                                img_person.visibility = View.GONE
                                tv_elevation_count.visibility = View.GONE
                                tv_elevation_div.visibility = View.GONE
                                tv_elevation_count_total.visibility = View.GONE
                                btn_left.visibility = View.GONE
                                btn_right.visibility = View.GONE

                            } else {
                                name = eval_mate[eval_count]
                                var count = eval_count + 1
                                var count_string = count.toString()

                                tv_elevation_intro.text = "<b>${name}님</b>과의<br>동행은 어떠셨나요?".toSpanned()
                                btn_elevation_top.setText("좋았어요")
                                btn_elevation_bottom.setText("별로였어요")
                                tv_elevation_count.setText(count_string)
                                img_close_button.visibility = View.VISIBLE
                                if(count > 1) {
                                    btn_left.visibility = View.VISIBLE
                                }
                                if(count == eval_count_total) {
                                    btn_right.visibility = View.GONE
                                }
                            }
                        }
                    }
                    return false
                }
            })

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
