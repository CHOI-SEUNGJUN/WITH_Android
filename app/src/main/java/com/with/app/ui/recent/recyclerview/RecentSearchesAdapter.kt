package com.with.app.ui.recent.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.with.app.R
import com.with.app.manage.RecentSearchesHelper


class RecentSearchesAdapter (private val context : Context,
                             private val dbHelper : RecentSearchesHelper,
                             private val noDataInterface: NoDataInterface) : RecyclerView.Adapter<RecentSearchesViewHolder>() {

    var item = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchesViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_recent_searches, parent, false)

        return RecentSearchesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecentSearchesViewHolder, position: Int) {
        holder.tv_keyword.text = item[position]
        holder.btn_delete.setOnClickListener {
            dbHelper.deleteKeyword(item[position])
            item.clear()
            item = dbHelper.readKeyword()
            if (item.isEmpty()) {
                noDataInterface.noData(true)
            }
            notifyDataSetChanged()
        }
    }

}

interface NoDataInterface {
    fun noData(no : Boolean)
}