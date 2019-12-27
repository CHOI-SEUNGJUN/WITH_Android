package com.with.app.ui.postlist

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.with.app.R
import kotlinx.android.synthetic.main.activity_chagne_region.*
import kotlinx.android.synthetic.main.fragment_region_tabbar.*

class ChangeRegionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chagne_region)
        val text = SpannableString("떠나고 싶은 곳을\n선택해주세요")
        text.setSpan(StyleSpan(Typeface.BOLD), 0, 8, 0)
        tv_region_intro.setText(text)

        configureTopNavigation()
    }


    private fun configureTopNavigation() {
        var textViewList : ArrayList<TextView?> = arrayListOf(tv_region_tabbar_idx0,tv_region_tabbar_idx1, tv_region_tabbar_idx2, tv_region_tabbar_idx3, tv_region_tabbar_idx4, tv_region_tabbar_idx5, tv_region_tabbar_idx6)

//        val adapter = BannerPagerAdapter(bannerList)
//        vp_banner.adapter = adapter

        val adapter = FragmentRegionPagerAdapter(supportFragmentManager)
        vp_region.adapter = adapter

        tl_region_tabbar.setupWithViewPager(vp_region)

        val topNaviLayout: View = this.layoutInflater.inflate(R.layout.fragment_region_tabbar, null, false)
//
        tl_region_tabbar.getTabAt(0)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx0) as ConstraintLayout
        tl_region_tabbar.getTabAt(1)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx1) as ConstraintLayout
        tl_region_tabbar.getTabAt(2)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx2) as ConstraintLayout
        tl_region_tabbar.getTabAt(3)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx3) as ConstraintLayout
        tl_region_tabbar.getTabAt(4)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx4) as ConstraintLayout
        tl_region_tabbar.getTabAt(5)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx5) as ConstraintLayout
        tl_region_tabbar.getTabAt(6)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx6) as ConstraintLayout

        tv_region_tabbar_idx0.setTextColor(Color.parseColor("#000000"))
        tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
        tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
        tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
        tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
        tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
        tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

        ind_region_0.visibility = View.VISIBLE
        ind_region_1.visibility = View.GONE
        ind_region_2.visibility = View.GONE
        ind_region_3.visibility = View.GONE
        ind_region_4.visibility = View.GONE
        ind_region_5.visibility = View.GONE
        ind_region_6.visibility = View.GONE


        vp_region.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tl_region_tabbar))
        tl_region_tabbar.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                var i = tab.position
                vp_region.currentItem = i


//                when(i) {
//                    0 ->
//                }

                tabIconSelect()

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

//        val topNaviLayout: View = this.layoutInflater.inflate(R.layout.fragment_region_tabbar, null, false)
//
//        tl_region_tabbar.getTabAt(0)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx0) as ConstraintLayout
//        tl_region_tabbar.getTabAt(1)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx1) as ConstraintLayout
//        tl_region_tabbar.getTabAt(2)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx2) as ConstraintLayout
//        tl_region_tabbar.getTabAt(3)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx3) as ConstraintLayout
//        tl_region_tabbar.getTabAt(4)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx4) as ConstraintLayout
//        tl_region_tabbar.getTabAt(5)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx5) as ConstraintLayout
//        tl_region_tabbar.getTabAt(6)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx6) as ConstraintLayout

        //tabIconSelect(i)
    }

    private fun tabIconSelect() {
//        val topNaviLayout: View = this.layoutInflater.inflate(R.layout.fragment_region_tabbar, null, false)
//        tl_region_tabbar.getTabAt(0)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx0) as ConstraintLayout
//        tl_region_tabbar.getTabAt(1)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx1) as ConstraintLayout
//        tl_region_tabbar.getTabAt(2)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx2) as ConstraintLayout
//        tl_region_tabbar.getTabAt(3)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx3) as ConstraintLayout
//        tl_region_tabbar.getTabAt(4)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx4) as ConstraintLayout
//        tl_region_tabbar.getTabAt(5)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx5) as ConstraintLayout
//        tl_region_tabbar.getTabAt(6)!!.customView = topNaviLayout.findViewById(R.id.cl_region_frag_idx6) as ConstraintLayout
//
        val tab_0 = tl_region_tabbar.getTabAt(0)!!
        val tab_1 = tl_region_tabbar.getTabAt(1)!!
        val tab_2 = tl_region_tabbar.getTabAt(2)!!
        val tab_3 = tl_region_tabbar.getTabAt(3)!!
        val tab_4 = tl_region_tabbar.getTabAt(4)!!
        val tab_5 = tl_region_tabbar.getTabAt(5)!!
        val tab_6 = tl_region_tabbar.getTabAt(6)!!

        if(tab_0.isSelected) {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#000000"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

            ind_region_0.visibility = View.VISIBLE
            ind_region_1.visibility = View.GONE
            ind_region_2.visibility = View.GONE
            ind_region_3.visibility = View.GONE
            ind_region_4.visibility = View.GONE
            ind_region_5.visibility = View.GONE
            ind_region_6.visibility = View.GONE

            Toast.makeText(this,"0", Toast.LENGTH_SHORT).show()
        }
        else if(tab_1.isSelected) {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#000000"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

            ind_region_0.visibility = View.GONE
            ind_region_1.visibility = View.VISIBLE
            ind_region_2.visibility = View.GONE
            ind_region_3.visibility = View.GONE
            ind_region_4.visibility = View.GONE
            ind_region_5.visibility = View.GONE
            ind_region_6.visibility = View.GONE
            Toast.makeText(this,"1", Toast.LENGTH_SHORT).show()
        }
        else if(tab_2.isSelected) {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#000000"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

            ind_region_0.visibility = View.GONE
            ind_region_1.visibility = View.GONE
            ind_region_2.visibility = View.VISIBLE
            ind_region_3.visibility = View.GONE
            ind_region_4.visibility = View.GONE
            ind_region_5.visibility = View.GONE
            ind_region_6.visibility = View.GONE
            Toast.makeText(this,"2", Toast.LENGTH_SHORT).show()
        }
        else if(tab_3.isSelected) {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#000000"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

            ind_region_0.visibility = View.GONE
            ind_region_1.visibility = View.GONE
            ind_region_2.visibility = View.GONE
            ind_region_3.visibility = View.VISIBLE
            ind_region_4.visibility = View.GONE
            ind_region_5.visibility = View.GONE
            ind_region_6.visibility = View.GONE
            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
        }
        else if(tab_4.isSelected) {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#000000"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

            ind_region_0.visibility = View.GONE
            ind_region_1.visibility = View.GONE
            ind_region_2.visibility = View.GONE
            ind_region_3.visibility = View.GONE
            ind_region_4.visibility = View.VISIBLE
            ind_region_5.visibility = View.GONE
            ind_region_6.visibility = View.GONE
            Toast.makeText(this,"4", Toast.LENGTH_SHORT).show()
        }
        else if(tab_5.isSelected) {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#000000"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#a8a8a8"))

            ind_region_0.visibility = View.GONE
            ind_region_1.visibility = View.GONE
            ind_region_2.visibility = View.GONE
            ind_region_3.visibility = View.GONE
            ind_region_4.visibility = View.GONE
            ind_region_5.visibility = View.VISIBLE
            ind_region_6.visibility = View.GONE
            Toast.makeText(this,"5", Toast.LENGTH_SHORT).show()
        }
        else if(tab_6.isSelected)  {
            tv_region_tabbar_idx0.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx1.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx2.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx3.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx4.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx5.setTextColor(Color.parseColor("#a8a8a8"))
            tv_region_tabbar_idx6.setTextColor(Color.parseColor("#000000"))
            ind_region_0.visibility = View.GONE
            ind_region_1.visibility = View.GONE
            ind_region_2.visibility = View.GONE
            ind_region_3.visibility = View.GONE
            ind_region_4.visibility = View.GONE
            ind_region_5.visibility = View.GONE
            ind_region_6.visibility = View.VISIBLE
            Toast.makeText(this,"6", Toast.LENGTH_SHORT).show()
        }

    }




}
