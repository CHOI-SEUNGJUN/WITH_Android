package com.with.app.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.with.app.R
import com.with.app.ui.home.recyclerview.*
import com.with.app.ui.postlist.PostListFragment
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var withMateAdapter : WithMateAdapter
    private lateinit var recPlaceAdapter : RecPlaceAdapter
    private lateinit var recBulletinAdapter : RecentBulletinAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeWithMateList()
        makeRecPlace()
        makeRecentBulletin()

        val adapter = BannerPagerAdapter(bannerList)
        vp_banner.adapter = adapter

        tab_layout.setupWithViewPager(vp_banner, true)

        btn_mate.setOnClickListener {
            val fragment_post_list = PostListFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_container, fragment_post_list)
                ?.commit()
        }

    }

    companion object {
        val bannerList = arrayListOf(
            Banner("img1"),
            Banner("img3"),
            Banner("img2")
        )
    }

    private fun makeWithMateList() {
        withMateAdapter = WithMateAdapter(context!!)

        rv_with_mate.adapter = withMateAdapter

        rv_with_mate.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)

        withMateAdapter.mate = listOf(
            WithMateItem(
                name = "김은별"
                //profile_url =  ""
            ),
            WithMateItem(
                name = "김은별"
            ),
            WithMateItem(
                name = "김은별"
            ),
            WithMateItem(
                name = "김은별"
            ),
            WithMateItem(
                name = "김은별"
            ),
            WithMateItem(
                name = "김은별"
            ),
            WithMateItem(
                name = "김은별"
            ),
            WithMateItem(
                name = "김은별"
            )
        )
        withMateAdapter.notifyDataSetChanged()
    }

    private fun makeRecPlace() {
        recPlaceAdapter = RecPlaceAdapter(context!!)

        rv_recommend_place.adapter = recPlaceAdapter

        rv_recommend_place.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)

        recPlaceAdapter.recPlace = listOf(
            RecPlaceItem(
                place = "FRANCE"
                //profile_url =  ""
            ),
            RecPlaceItem(
                place = "FRANCE"
                //profile_url =  ""
            ),
            RecPlaceItem(
                place = "FRANCE"
                //profile_url =  ""
            ),
            RecPlaceItem(
                place = "FRANCE"
                //profile_url =  ""
            ),
            RecPlaceItem(
                place = "FRANCE"
                //profile_url =  ""
            ),
            RecPlaceItem(
                place = "FRANCE"
                //profile_url =  ""
            ),
            RecPlaceItem(
                place = "PARIS"
                //profile_url =  ""
            )
        )
        recPlaceAdapter.notifyDataSetChanged()
    }

    private fun makeRecentBulletin() {
        recBulletinAdapter = RecentBulletinAdapter(context!!)

        rv_recent_bulletin.adapter = recBulletinAdapter

        rv_recent_bulletin.layoutManager = GridLayoutManager(context!!, 2)

        recBulletinAdapter.bulletin = listOf(
            RecentBulletinItem(
                name = "김은별",
                place = "남아프리카공화국",
                desc = "메리크리스마스><"
                //profile_url =  ""
            ),
            RecentBulletinItem(
                name = "김은별",
                place = "남아프리카공화국",
                desc = "메리크리스마스 메리크리스마스 메리크리스마스"
                //profile_url =  ""
            ),
            RecentBulletinItem(
                name = "김은별",
                place = "남아프리카공화국",
                desc = "메리크리스마스><"
                //profile_url =  ""
            ),
            RecentBulletinItem(
                name = "김은별",
                place = "남아프리카공화국",
                desc = "메리크리스마스><"
                //profile_url =  ""
            ),
            RecentBulletinItem(
                name = "김은별",
                place = "남아프리카공화국",
                desc = "메리크리스마스><"
                //profile_url =  ""
            ),
            RecentBulletinItem(
                name = "김은별",
                place = "남아프리카공화국",
                desc = "메리크리스마스><"
                //profile_url =  ""
            )
        )
        recBulletinAdapter.notifyDataSetChanged()
    }


}
