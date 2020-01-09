package com.kkhura.simppler.sampleproject.ui.home.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kkhura.simppler.sampleproject.AppApplication
import com.kkhura.simppler.sampleproject.BaseFragment
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.ALBUM
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.ALBUM_TITLE
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.ALBUM_URL
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.SELECTED_ALBUM_INDEX
import com.kkhura.simppler.sampleproject.ui.home.adapter.TrackListAdapter
import com.kkhura.simppler.sampleproject.ui.home.model.Item_
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_album_details.*

class AlbumDetailFragment : BaseFragment() {

    private var url: String? = null
    private var album: ArrayList<Item_>? = null
    private var selectedAlbumIndex: Int = 0
    override val title: String
        get() = "Artist"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_album_details, container, false)
        (activity.application as AppApplication).networkComponent.inject(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        val args: Bundle = getArguments()
        selectedAlbumIndex = args.getInt(SELECTED_ALBUM_INDEX)
        album = args.getParcelableArrayList<Item_>(ALBUM)
        url = args.getString(ALBUM_URL)
        loadImage()

        album?.let {
            tv_artists.text = args.getString(ALBUM_TITLE)
            rv_tracks?.layoutManager = LinearLayoutManager(getActivity())
            val tracksAdapter = TrackListAdapter(getActivity(), it)
            rv_tracks!!.adapter = tracksAdapter
        }
    }

    fun loadImage() {
        url?.let {
            Picasso.get()
                    .load(it)
                    .into(img_album_cover)
        }
    }
}