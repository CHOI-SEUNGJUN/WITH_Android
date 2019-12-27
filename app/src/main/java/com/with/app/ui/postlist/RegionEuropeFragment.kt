package com.with.app.ui.postlist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.with.app.R
import kotlinx.android.synthetic.main.fragment_region_europe.*

/**
 * A simple [Fragment] subclass.
 */
class RegionEuropeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_europe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_europe_all.setOnTouchListener { _: View, event:MotionEvent ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn_europe_all.setBackgroundResource(R.drawable.africaall_selected_btn)
                }
                MotionEvent.ACTION_UP -> {
                    btn_europe_all.setBackgroundResource(R.drawable.westeurope_unselected_btn)
                }
            }
            true
        }
    }

    companion object {
        private const val num = "num"
        @JvmStatic
        fun newInstance(Number: Int): RegionEuropeFragment {
            return RegionEuropeFragment().apply {
                arguments = Bundle().apply {
                    putInt(num, Number)
                }
            }
        }
    }



}
