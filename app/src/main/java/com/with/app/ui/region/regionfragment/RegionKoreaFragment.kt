package com.with.app.ui.region.regionfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.ui.postlist.recyclerview.RegionListAdapter
import kotlinx.android.synthetic.main.fragment_region_europe.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class RegionKoreaFragment : Fragment() {

    private val requestManager : RequestManager by inject()

    private lateinit var regionListAdapter : RegionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_korea, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeRegionList()

    }

    companion object {
        private const val num = "num"
        @JvmStatic
        fun newInstance(Number: Int): RegionKoreaFragment {
            return RegionKoreaFragment().apply {
                arguments = Bundle().apply {
                    putInt(num, Number)
                }
            }
        }
    }

    private fun makeRegionList() {
        regionListAdapter = RegionListAdapter(context!!, requestManager.regionManager)

        rv_region_list.adapter = regionListAdapter

        rv_region_list.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

        // regionListAdapter.region
        regionListAdapter.notifyDataSetChanged()
    }


}
