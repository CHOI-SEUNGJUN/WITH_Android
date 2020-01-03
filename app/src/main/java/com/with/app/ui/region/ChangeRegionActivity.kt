package com.with.app.ui.region

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.tabs.TabLayout
import com.with.app.R
import com.with.app.extension.toSpanned
import kotlinx.android.synthetic.main.activity_change_region.*
import kotlinx.android.synthetic.main.fragment_region_tabbar.*

class ChangeRegionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_region)
        val spanText = "<b>떠나고 싶은 곳</b>을<br>선택해주세요".toSpanned()
        tv_region_intro.text = spanText

        configureTopNavigation()
    }


    private fun configureTopNavigation() {
        val adapter = FragmentRegionPagerAdapter(
            supportFragmentManager
        )
        vp_region.adapter = adapter

        tl_region_tabbar.setupWithViewPager(vp_region)

        val topNaviLayout: View = this.layoutInflater.inflate(R.layout.fragment_region_tabbar, null, false)

        tl_region_tabbar.getTabAt(0)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx0) as ConstraintLayout
        tl_region_tabbar.getTabAt(1)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx1) as ConstraintLayout
        tl_region_tabbar.getTabAt(2)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx2) as ConstraintLayout
        tl_region_tabbar.getTabAt(3)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx3) as ConstraintLayout
        tl_region_tabbar.getTabAt(4)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx4) as ConstraintLayout
        tl_region_tabbar.getTabAt(5)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx5) as ConstraintLayout
        tl_region_tabbar.getTabAt(6)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx6) as ConstraintLayout

        vp_region.currentItem = 0

        ind_region_0.visibility = View.VISIBLE
        tv_region_tabbar_idx0.setTextColor(Color.parseColor("#000000"))

        vp_region.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tl_region_tabbar))
        tl_region_tabbar.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                var i = tab.position
                vp_region.currentItem = i
                when(i) {
                    0 -> {
                        ind_region_0.visibility = View.VISIBLE
                        tv_region_tabbar_idx0.setTextColor(Color.parseColor("#000000"))
                    }
                    1 -> {
                        ind_region_1.visibility = View.VISIBLE
                        tv_region_tabbar_idx1.setTextColor(Color.parseColor("#000000"))
                    }
                    2 -> {
                        ind_region_2.visibility = View.VISIBLE
                        tv_region_tabbar_idx2.setTextColor(Color.parseColor("#000000"))
                    }
                    3 -> {
                        ind_region_3.visibility = View.VISIBLE
                        tv_region_tabbar_idx3.setTextColor(Color.parseColor("#000000"))
                    }
                    4 -> {
                        ind_region_4.visibility = View.VISIBLE
                        tv_region_tabbar_idx4.setTextColor(Color.parseColor("#000000"))
                    }
                    5 -> {
                        ind_region_5.visibility = View.VISIBLE
                        tv_region_tabbar_idx5.setTextColor(Color.parseColor("#000000"))
                    }
                    6 -> {
                        ind_region_6.visibility = View.VISIBLE
                        tv_region_tabbar_idx6.setTextColor(Color.parseColor("#000000"))
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                var i= tab.position
                vp_region.currentItem = i

                when(i) {
                    0 -> {
                        ind_region_0.visibility = View.GONE
                        tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                    1 -> {
                        ind_region_1.visibility = View.GONE
                        tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                    2 -> {
                        ind_region_2.visibility = View.GONE
                        tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                    3 -> {
                        ind_region_3.visibility = View.GONE
                        tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                    4 -> {
                        ind_region_4.visibility = View.GONE
                        tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                    5 -> {
                        ind_region_5.visibility = View.GONE
                        tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                    6 -> {
                        ind_region_6.visibility = View.GONE
                        tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))
                    }
                }
            }
        })
    }
}





