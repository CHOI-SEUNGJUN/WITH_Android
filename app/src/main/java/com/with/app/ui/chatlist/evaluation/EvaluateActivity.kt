package com.with.app.ui.chatlist.evaluation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.with.app.R
import com.with.app.ui.postlist.ChangeRegionActivity
import com.with.app.util.toast
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.item_post_bulletin.*

class EvaluateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)

        var eval_mate = ArrayList<String>()
        eval_mate.add("석영현")
        eval_mate.add("최승준")
        eval_mate.add("조현아")
        eval_mate.add("안현준")


        //tv_elevation_count.setText("$eval_count")

        val eval_count_total = eval_mate.size
        var name = eval_mate[0]
        tv_elevation_intro.setText("$name" + "님과의\n동행은 어떠셨나요?")
        tv_elevation_count_total.setText("$eval_count_total")

        img_close_button.setOnClickListener{
            finish()
        }


        btn_elevation_bottom.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                var eval_count = tv_elevation_count.text.toString().toInt()
                var bottom_text = btn_elevation_bottom.text.toString()

                var end = false
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        btn_elevation_bottom.setBackgroundColor(Color.parseColor("#fd9f08"))
                        if(bottom_text == "위드하기") {
                            val intent = Intent(this@EvaluateActivity, ChangeRegionActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }

                    MotionEvent.ACTION_UP -> {
                        btn_elevation_bottom.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                        if(bottom_text == "위드하기") {
                        }
                        else if(eval_count == eval_count_total) {
                            img_close_button.visibility = View.GONE
                            tv_elevation_intro.setText("당신의 여행에\n더 멋진 동행이\n기다리고 있어요!")
                            btn_elevation_bottom.setText("위드하기")
                            btn_elevation_top.visibility = View.GONE
                        } else {
                            tv_elevation_intro.setText("당신의 여행에\n더 멋진 동행이\n기다리고 있어요!")
                            img_close_button.visibility = View.GONE
                            btn_elevation_top.setText("다른 동행은 어떠셨나요?")
                            btn_elevation_bottom.setText("위드하기")
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
                        btn_elevation_top.setBackgroundColor(Color.parseColor("#fd9f08"))
                    }

                    MotionEvent.ACTION_UP -> {
                        btn_elevation_top.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                        if(eval_count == eval_count_total) {
                            tv_elevation_intro.setText("앞으로도\n위드와 함께해요")
                            btn_elevation_bottom.setText("위드하기")
                            btn_elevation_top.visibility = View.GONE
                            img_close_button.visibility = View.GONE

                        } else {
                            if(top_text == "다른 동행은 어떠셨나요?") {
                                name = eval_mate[eval_count]
                                var count = eval_count + 1
                                var count_string = count.toString()
                                tv_elevation_intro.setText("$name"+"님과의\n동행은 어떠셨나요?")
                                btn_elevation_top.setText("좋았어요")
                                btn_elevation_bottom.setText("별로였어요")
                                tv_elevation_count.setText(count_string)
                                img_close_button.visibility = View.VISIBLE
                            }
                            else {
                                tv_elevation_intro.setText("앞으로도\n위드와 함께해요")
                                btn_elevation_top.setText("다른 동행은 어떠셨나요?")
                                btn_elevation_bottom.setText("위드하기")
                                img_close_button.visibility = View.GONE
                            }
                        }


                    }
                }
                return false
            }
        })
    }
}
