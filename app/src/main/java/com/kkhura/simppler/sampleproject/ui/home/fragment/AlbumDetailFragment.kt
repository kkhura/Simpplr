package com.kkhura.simppler.sampleproject.ui.home.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kkhura.simppler.sampleproject.BaseFragment
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.ALBUM_TITLE
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.SELECTED_ALBUM_INDEX
import com.kkhura.simppler.sampleproject.ui.home.adapter.TrackListAdapter
import com.kkhura.simppler.sampleproject.ui.home.view.HomeActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_album_details.*

class AlbumDetailFragment : BaseFragment() {

    private var selectedAlbumIndex: Int = 0
    override var title: String = "Artist"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_album_details, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: Bundle = getArguments()
        selectedAlbumIndex = args.getInt(SELECTED_ALBUM_INDEX)
        val url = (activity as HomeActivity).homeActivityViewModel.albumContainerModel.items[selectedAlbumIndex].album.images[0].url
        loadImage(url)

        val items = (activity as HomeActivity).homeActivityViewModel.albumContainerModel.items[selectedAlbumIndex].album.tracks.items

        tv_artists.text = args.getString(ALBUM_TITLE)
        title = args.getString(ALBUM_TITLE)
        rv_tracks?.layoutManager = LinearLayoutManager(getActivity())
        val tracksAdapter = TrackListAdapter(getActivity(), items)
        rv_tracks!!.adapter = tracksAdapter
    }

    fun loadImage(url: String) {
        Picasso.get()
                .load(url)
                .into(img_album_cover)
    }
}