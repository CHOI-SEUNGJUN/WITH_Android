package com.with.app.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.with.app.R
import com.with.app.manage.RecentViewsHelper
import com.with.app.manage.RequestManager
import com.with.app.ui.home.recyclerview.*
import com.with.app.ui.postlist.PostListFragment
import com.with.app.ui.region.ChangeRegionActivity
import com.with.app.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val requestManager: RequestManager by inject()
    private lateinit var recentViewsHelper : RecentViewsHelper

    private lateinit var withMateAdapter : WithMateAdapter
    private lateinit var recPlaceAdapter : RecPlaceAdapter
    private lateinit var recBulletinAdapter : RecentBulletinAdapter

    private val requestOptions = RequestOptions()
        .placeholder(R.drawable.home_img)
        .fallback(R.drawable.home_img)
        .error(R.drawable.home_img)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentViewsHelper = RecentViewsHelper(context!!)
        makeWithMateList()
        makeRecPlace()
        makeRecentBulletin()
        getBgImg()

        val bannerList = arrayListOf(
            Banner("img1"),
            Banner("img3"),
            Banner("img2")
        )

        val adapter = BannerPagerAdapter(bannerList)
        vp_banner.adapter = adapter

        tab_layout.setupWithViewPager(vp_banner, true)

        btn_mate.setOnClickListener {
            if (requestManager.regionManager.code.isEmpty()) {
                val intent = Intent(context, ChangeRegionActivity::class.java)
                startActivityForResult(intent, REGIONCHANGE_REQCODE)
            } else {
                val fragment_post_list = PostListFragment()
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.replace(R.id.main_container, fragment_post_list)
                    ?.commit()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REGIONCHANGE_REQCODE && resultCode == Activity.RESULT_OK) {
            val fragment_post_list = PostListFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.main_container, fragment_post_list)
                ?.commit()
        }
    }

    private fun getBgImg() {
        requestManager.requestBgImg()
            .safeEnqueue (
                onSuccess = {
                    it.let {
                        Glide.with(context!!)
                            .load(it.data.regionImgH)
                            .apply(requestOptions)
                            .into(img_main_background)
                    }
                }
        )
    }

    private fun makeWithMateList() {
        withMateAdapter = WithMateAdapter(context!!)

        rv_with_mate.adapter = withMateAdapter

        rv_with_mate.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        requestManager.requestWithMate()
            .safeEnqueue (
                onSuccess = {
                    Log.e("data", it.data.toString())
                    if (it.data.toString().isNullOrEmpty()) {
                        tv_with_mate.visibility = View.GONE
                        rv_with_mate.visibility = View.GONE
                    } else {
                        withMateAdapter.mate = it.data
                        withMateAdapter.notifyDataSetChanged()
                    }
                }
            )

    }

    private fun makeRecPlace() {
        recPlaceAdapter = RecPlaceAdapter(context!!)

        rv_recommend_place.adapter = recPlaceAdapter

        rv_recommend_place.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)

        var regionCode = ""
        if (requestManager.regionManager.code.isEmpty()) {
            regionCode = "000000"
        } else {
            regionCode = requestManager.regionManager.code
        }
        requestManager.requestRecommendPlace(regionCode)
            .safeEnqueue(
                onSuccess = {
                    recPlaceAdapter.recPlace = it.data
                    recPlaceAdapter.notifyDataSetChanged()
                }
            )
    }

    private fun makeRecentBulletin() {
        recBulletinAdapter = RecentBulletinAdapter(context!!)

        rv_recent_bulletin.adapter = recBulletinAdapter

        rv_recent_bulletin.layoutManager = GridLayoutManager(context!!, 2)
        var boardIdx = recentViewsHelper.readView()
        if (boardIdx.isEmpty()) {

        } else {
            requestManager.requestLatelyBoard("102+103+104+105+107+112")
                .safeEnqueue(
                    onSuccess = {
                        recBulletinAdapter.bulletin = it.data
                        recBulletinAdapter.notifyDataSetChanged()
                    }
                )
        }
    }

    companion object {
        const val REGIONCHANGE_REQCODE = 666
    }

}
