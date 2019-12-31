package com.with.app.ui.region


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.with.app.ui.region.regionfragment.*

class FragmentRegionPagerAdapter (fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

   private val items = ArrayList<Fragment> ()
    init {
        items.add(RegionEuropeFragment.newInstance(0))
        items.add(RegionAsiaFragment.newInstance(1))
        items.add(RegionNorthAmericaFragment.newInstance(2))
        items.add(RegionSouthAmericaFragment.newInstance(3))
        items.add(RegionOceaniaFragment.newInstance(4))
        items.add(RegionAfricaFragment.newInstance(5))
        items.add(RegionKoreaFragment.newInstance(6))

    }


    override fun getItem(position: Int): Fragment {

        return items[position]
    }



    override fun getCount(): Int {
        return items.size
    }
}