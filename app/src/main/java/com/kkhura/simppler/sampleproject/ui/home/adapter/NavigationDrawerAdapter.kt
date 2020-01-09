package com.kkhura.simppler.sampleproject.ui.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.ui.home.listner.AdapterOnItemClickable

class NavigationDrawerAdapter(private val mContext: Context, private val list: Array<String>,
                              itemClickedListener: AdapterOnItemClickable) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemClickedListener: AdapterOnItemClickable

    init {
        this.itemClickedListener = itemClickedListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.navigation_drawer_list_item,
                viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(customViewHolder: RecyclerView.ViewHolder, position: Int) {
        if (customViewHolder is ViewHolder) {
            customViewHolder.tvNavItem.text = list[position]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val tvNavItem: TextView

        init {
            tvNavItem = view.findViewById(R.id.tv_nav_item)
            view.setOnClickListener { itemClickedListener.onItemClicked(view, adapterPosition) }
        }
    }
}