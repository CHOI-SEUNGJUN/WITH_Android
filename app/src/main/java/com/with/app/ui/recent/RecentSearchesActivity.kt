package com.with.app.ui.recent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.with.app.R
import com.with.app.manage.RecentSearchesHelper
import com.with.app.ui.recent.recyclerview.NoDataInterface
import com.with.app.ui.recent.recyclerview.RecentSearchesAdapter
import kotlinx.android.synthetic.main.activity_recent_searches.*

class RecentSearchesActivity : AppCompatActivity(),NoDataInterface {

    private lateinit var adapter : RecentSearchesAdapter
    private lateinit var lm : LinearLayoutManager
    private lateinit var dbHelper : RecentSearchesHelper
    private var data : ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_searches)
        init()
    }

    private fun init() {
        dbHelper = RecentSearchesHelper(applicationContext)
        adapter = RecentSearchesAdapter(applicationContext, dbHelper, this)
        lm = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rv_recentSearches.adapter = adapter
        rv_recentSearches.layoutManager = lm

        data = dbHelper.readKeyword()
        if (data.isEmpty()) {
            tv_noData.visibility = View.VISIBLE
            btn_allDelete.visibility = View.GONE
        } else {
            adapter.item = data
            adapter.notifyDataSetChanged()
            tv_noData.visibility = View.GONE
            btn_allDelete.visibility = View.VISIBLE
        }

        btn_search.setOnClickListener {
            val keyword = edt_search.text.toString()
            dbHelper.insertKeyword(keyword)
            val intent = Intent()
            intent.putExtra("keyword", keyword)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        btn_allDelete.setOnClickListener {
            dbHelper.deleteAllKeyword()
            data.clear()
            adapter.notifyDataSetChanged()
            tv_noData.visibility = View.VISIBLE
            btn_allDelete.visibility = View.GONE
        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    override fun noData(no: Boolean) {
        tv_noData.visibility = View.VISIBLE
        btn_allDelete.visibility = View.GONE
    }
}
