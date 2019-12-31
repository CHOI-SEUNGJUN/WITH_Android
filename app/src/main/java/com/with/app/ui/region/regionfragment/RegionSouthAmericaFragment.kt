package com.with.app.ui.region.regionfragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.ui.postlist.recyclerview.RegionListAdapter
import com.with.app.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_region_europe.*
import kotlinx.android.synthetic.main.fragment_region_europe.rv_region_list
import kotlinx.android.synthetic.main.fragment_region_south_america.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class RegionSouthAmericaFragment : Fragment(), View.OnClickListener {

    private val requestManager : RequestManager by inject()

    private lateinit var regionListAdapter : RegionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_south_america, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        makeRegionList()

    }

    private fun init() {
        btn_south_america_all.setOnClickListener(this)
    }

    private fun makeRegionList() {
        regionListAdapter = RegionListAdapter(context!!, requestManager.regionManager)

        rv_region_list.adapter = regionListAdapter

        rv_region_list.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

        getDataWhenClick(0)
    }

    private fun getDataWhenClick(mode : Int) {
        var regionCode : String = ""
        when(mode) {
            0 ->
                regionCode = "040000"
        }

        requestManager.requestCountryList(regionCode)
            .safeEnqueue(
                onSuccess = {
                    if(regionListAdapter.region.isNotEmpty()) {
                        regionListAdapter.region = listOf()
                    }
                    regionListAdapter.region = it.data
                    regionListAdapter.notifyDataSetChanged()
                },
                onError = {
                    Log.e("error", it.toString())
                }
            )
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_south_america_all ->
                getDataWhenClick(0)
        }
    }

    companion object {
        private const val num = "num"
        @JvmStatic
        fun newInstance(Number: Int): RegionSouthAmericaFragment {
            return RegionSouthAmericaFragment()
                .apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
        }
    }

}
