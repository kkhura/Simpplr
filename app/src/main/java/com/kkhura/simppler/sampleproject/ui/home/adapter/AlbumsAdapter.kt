package com.kkhura.simppler.sampleproject.ui.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.ui.home.listner.AdapterOnItemClickable
import com.kkhura.simppler.sampleproject.ui.home.model.Item
import com.squareup.picasso.Picasso

class AlbumsAdapter(context: Context, list: List<Item>?,
                    itemClickedListener: AdapterOnItemClickable) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext: Context
    private val list: List<Item>?
    private var isVisible = false
    private val VIEW_TYPE_ITEM = 1
    private val itemClickedListener: AdapterOnItemClickable

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        var holder: RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.album_row, viewGroup, false)
            holder = ViewHolder(view, mContext)
        }
        return holder!!
    }

    override fun onBindViewHolder(customViewHolder: RecyclerView.ViewHolder, position: Int) {
        if (customViewHolder is ViewHolder) {
            if (position == 0) {
                customViewHolder.tempView.visibility = View.VISIBLE
            } else {
                customViewHolder.tempView.visibility = View.GONE
            }
            list?.let { list ->
                customViewHolder.tvArtist.setText(list[position].album.name)
                customViewHolder.tvDuration.setText(list[position].album.type)
                Picasso.get()
                        .load(list[position].album.images.get(0).url)
                        .resize(100, 100)
                        .centerCrop()
                        .into(customViewHolder.ivAlbumCover)
            }
        }
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        }
        return if (isVisible) {
            list.size + 1
        } else {
            list.size
        }
    }

    internal inner class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        val tvArtist: TextView
        val tvDuration: TextView
        val ivAlbumCover: ImageView
        val tempView: FrameLayout

        init {
            tvArtist = view.findViewById(R.id.tv_artist)
            tvDuration = view.findViewById(R.id.tv_duration)
            ivAlbumCover = view.findViewById(R.id.img_album_cover)
            tempView = view.findViewById(R.id.temp_view)
            view.setOnClickListener { itemClickedListener.onItemClicked(view, adapterPosition) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    init {
        this.list = list
        mContext = context
        this.itemClickedListener = itemClickedListener
    }
}