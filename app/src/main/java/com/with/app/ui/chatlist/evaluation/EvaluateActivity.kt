package com.with.app.ui.chatlist.evaluation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.with.app.R
import kotlinx.android.synthetic.main.activity_evaluate.*

class EvaluateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)

        


        btn_elevation_bottom.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        btn_elevation_bottom.setBackgroundColor(Color.parseColor("#fd9f08"))
                    }

                    MotionEvent.ACTION_UP -> {
                        btn_elevation_bottom.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                    }
                }
                return false
            }
        })

        btn_elevation_top.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        btn_elevation_top.setBackgroundColor(Color.parseColor("#fd9f08"))
                    }

                    MotionEvent.ACTION_UP -> {
                        btn_elevation_top.setBackgroundColor(Color.parseColor("#4DFFFFFF"))
                    }
                }
                return false
            }
        })
    }
}
