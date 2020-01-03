package com.with.app.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.with.app.R
import com.with.app.data.remote.ResponseChatListArrayData
import com.with.app.extension.*
import com.with.app.manage.RecentViewsHelper
import com.with.app.manage.RequestManager
import com.with.app.ui.home.recyclerview.*
import com.with.app.ui.postlist.PostListFragment
import com.with.app.ui.region.ChangeRegionActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(), PlaceClickListener {

    private val requestManager: RequestManager by inject()
    private lateinit var recentViewsHelper : RecentViewsHelper

    private lateinit var withMateAdapter : WithMateAdapter
    private lateinit var recPlaceAdapter : RecPlaceAdapter
    private lateinit var recBulletinAdapter : RecentBulletinAdapter

    private val postListFragment = PostListFragment()

    private var withMateData : MutableList<ResponseChatListArrayData> = mutableListOf()

    private val requestOptions = RequestOptions()
        .placeholder(R.drawable.home_img)
        .fallback(R.drawable.home_img)
        .error(R.drawable.home_img)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading.playAnimation()
        loading.loop(true)

        recentViewsHelper = RecentViewsHelper(context!!)
        makeWithMateList()
        makeRecPlace()
        makeRecentBulletin()
        getBgImg()

        val bannerList = arrayListOf(
            Banner("event_img"),
            Banner("event_img_2"),
            Banner("event_img_3")
        )

        val adapter = BannerPagerAdapter(bannerList)
        vp_banner.adapter = adapter

        tab_layout.setupWithViewPager(vp_banner, true)
        btn_mate.setOnClickListener { goWith() }
        btn_firstGoWith.setOnClickListener { goWith() }
    }

    private fun goWith(){
        if (requestManager.regionManager.code.isEmpty()) {
            val intent = Intent(context, ChangeRegionActivity::class.java)
            startActivityForResult(intent, REGIONCHANGE_REQCODE)
        } else goToPostList()
    }

    private fun getBgImg() {
        requestManager.requestBgImg()
            .safeEnqueue (
                onSuccess = {
                    it.let {
                        img_main_background.load(context!!, it.data.regionImgH, requestOptions)
                        loading.pauseAnimation()
                        homeContainer.visible()
                        loadingContainer.gone()
                    }
                })
    }

    private fun makeWithMateList() {
        withMateAdapter = WithMateAdapter(context!!)
        rv_with_mate.adapter = withMateAdapter
        rv_with_mate.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        requestManager.requestChatList()
            .safeEnqueue(
                onSuccess = {
                    if (it.success) {
                        for (item in it.data) if (item.withFlag == 1 && item.evalFlag == 1) withMateData.add(item)
                        if (withMateData.isEmpty()) {
                            tv_with_mate.gone()
                            rv_with_mate.gone()
                        } else {
                            // withMateData = withMateData.sortedWith(compareBy { it.withDate }).toMutableList()
                            withMateAdapter.mate = withMateData
                            withMateAdapter.notifyDataSetChanged()
                        }
                    }
                })
    }

    private fun makeRecPlace() {
        recPlaceAdapter = RecPlaceAdapter(context!!, this)
        rv_recommend_place.adapter = recPlaceAdapter
        rv_recommend_place.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val regionCode =
            if (requestManager.regionManager.code.isEmpty()) "000000"
            else requestManager.regionManager.code

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
        if (boardIdx.isNotEmpty()) {
            rv_recent_bulletin.visible()
            layout_noRecent.gone()
            requestManager.requestLatelyBoard(boardIdx)
                .safeEnqueue(
                    onSuccess = {
                        recBulletinAdapter.bulletin = it.data
                        recBulletinAdapter.notifyDataSetChanged()
                    })
        } else {
            rv_recent_bulletin.gone()
            layout_noRecent.visible()
        }
    }

    private fun goToPostList() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.main_container, postListFragment)
            ?.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REGIONCHANGE_REQCODE && resultCode == Activity.RESULT_OK) goToPostList()
    }

    override fun click(code: String, name: String) {
        requestManager.regionManager.code = code
        requestManager.regionManager.name = name
        goToPostList()
    }

    companion object {
        const val REGIONCHANGE_REQCODE = 666
    }

}
