package com.kkhura.simppler.sampleproject.ui.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.ui.home.model.Item_

class TrackListAdapter(context: Context, trackList: List<Item_>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext: Context
    private val tracklist: List<Item_>?
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.track_list_row, viewGroup, false)
        return ViewHolder(view, mContext)
    }

    override fun onBindViewHolder(customViewHolder: RecyclerView.ViewHolder, position: Int) {
        if (customViewHolder is ViewHolder) {
            if (position == 0) {
                customViewHolder.tempView.visibility = View.VISIBLE
            } else {
                customViewHolder.tempView.visibility = View.GONE
            }
            val builder = StringBuilder()
            tracklist?.get(position)?.let { item ->
                if (item.artists.size > 0) {
                    for (i in 0 until item.artists.size) {
                        builder.append(item.artists.get(i).name)
                        if (i != item.artists.size - 1) {
                            builder.append(",")
                        }
                    }
                }
            }
            customViewHolder.tvTrackTitle.setText(tracklist?.get(position)?.name)
            customViewHolder.tvTrackArtist.text = builder.toString()
            val minutes: Int? = (tracklist?.get(position)?.duration_ms?.div(1000) ?: 1) / 60
            val seconds: Int = (tracklist?.get(position)?.duration_ms?.div(1000) ?: 1) % 60
            customViewHolder.tvTrackDuration.text = minutes.toString() + " : " + seconds
        }
    }

    override fun getItemCount(): Int {
        return tracklist?.size ?: 0
    }

    internal inner class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        val tvTrackTitle: TextView
        val tvTrackDuration: TextView
        val tvTrackArtist: TextView
        val tempView: FrameLayout

        init {
            tvTrackTitle = view.findViewById(R.id.tv_track_title)
            tvTrackDuration = view.findViewById(R.id.tv_track_duration)
            tvTrackArtist = view.findViewById(R.id.tv_track_artist)
            tempView = view.findViewById(R.id.temp_view)
        }
    }

    init {
        tracklist = trackList
        mContext = context
    }
}