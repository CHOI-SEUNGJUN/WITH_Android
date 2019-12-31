package com.with.app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.ui.home.recyclerview.*
import com.with.app.ui.postlist.PostListFragment
import com.with.app.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val requestManager: RequestManager by inject()

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
                ?.addToBackStack(null)
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

        /*requestManager.requestWithMate(requestManager.authManager.token)
            .safeEnqueue (
                onSuccess = {
                    withMateAdapter.mate = it.data
                    withMateAdapter.notifyDataSetChanged()
                }
            )*/

    }

    private fun makeRecPlace() {
        recPlaceAdapter = RecPlaceAdapter(context!!)

        rv_recommend_place.adapter = recPlaceAdapter

        rv_recommend_place.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)

        requestManager.requestRecommendPlace("000000")
            .safeEnqueue(
                onSuccess = {
                    Log.e("OK?", "OK")
                    recPlaceAdapter.recPlace = it.data
                    recPlaceAdapter.notifyDataSetChanged()
                },
                onError = {
                    Log.e("error", it.toString())
                },
                onFailure = {
                    Log.e("failure", it.message())
                }
            )
    }

    private fun makeRecentBulletin() {
        recBulletinAdapter = RecentBulletinAdapter(context!!)

        rv_recent_bulletin.adapter = recBulletinAdapter

        rv_recent_bulletin.layoutManager = GridLayoutManager(context!!, 2)

        requestManager.requestLatelyBoard("102+103+104+105+107+112")
            .safeEnqueue(
                onSuccess = {
                    recBulletinAdapter.bulletin = it.data
                    recBulletinAdapter.notifyDataSetChanged()
                }
            )
    }

}
