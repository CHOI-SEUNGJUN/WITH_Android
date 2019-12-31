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
import kotlinx.android.synthetic.main.fragment_region_europe.rv_region_list
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class RegionAsiaFragment : Fragment() {

    private val requestManager : RequestManager by inject()

    private lateinit var regionListAdapter : RegionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_asia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // defaultAllRegionList()

      /*  // 전체 버튼 눌렀을 때
        btn_asia_all.setOnClickListener{
            makeRegionList(1)
        }

        // 서유럽 버튼 눌렀을 때
        btn_asia_west_south.setOnClickListener {
            makeRegionList(2)
        }*/

        // 동유럽

        makeRegionList()

    }


    companion object {
        private const val num = "num"
        @JvmStatic
        fun newInstance(Number: Int): RegionAsiaFragment {
            return RegionAsiaFragment().apply {
                arguments = Bundle().apply {
                    putInt(num, Number)
                }
            }
        }
    }

    // 처음에 디폴트로 실행되는 전체 리스트
    private fun defaultAllRegionList(){

        // 아시아 - all 리스트 받아오기(통신)
    }


    // 라디오 버튼 눌렀을 때 실행되는 리스트
    private fun makeRegionList() {


        regionListAdapter = RegionListAdapter(context!!, requestManager.regionManager)

        rv_region_list.adapter = regionListAdapter

        rv_region_list.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

        // regionListAdapter.region
        regionListAdapter.notifyDataSetChanged()
    }

}
