package com.with.app.ui.postlist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.with.app.R

/**
 * A simple [Fragment] subclass.
 */
class RegionNorthAmericaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_north_america, container, false)
    }

    companion object {
        private const val num = "num"
        @JvmStatic
        fun newInstance(Number: Int): RegionNorthAmericaFragment {
            return RegionNorthAmericaFragment().apply {
                arguments = Bundle().apply {
                    putInt(num, Number)
                }
            }
        }
    }


}
