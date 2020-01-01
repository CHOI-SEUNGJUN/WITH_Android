package com.with.app.ui.mypage


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.with.app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.fragment_my_page.*

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_setting.setOnClickListener {
            btn_cancle.visibility = View.VISIBLE
            tv_mypage.text = "개인정보 수정"
            cl_back.setBackgroundResource(R.drawable.edit_bg)
            activity?.img_disabled_navi?.visibility = View.VISIBLE
            edt_mypage_intro.isEnabled = true
            img_badge.visibility = View.INVISIBLE
            img_camera2.visibility = View.VISIBLE
            img_camera1.visibility = View.VISIBLE

        }

        edt_mypage_intro.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                line_Edt.visibility = View.VISIBLE
            } else {
                line_Edt.visibility = View.INVISIBLE
            }
        }

        img_camera1.setOnClickListener {

        }
    }


}
