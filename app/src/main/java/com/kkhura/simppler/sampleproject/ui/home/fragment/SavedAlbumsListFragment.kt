package com.kkhura.simppler.sampleproject.ui.home.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kkhura.simppler.sampleproject.AppApplication
import com.kkhura.simppler.sampleproject.BaseFragment
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants.ALBUM_URL
import com.kkhura.simppler.sampleproject.ui.home.adapter.AlbumsAdapter
import com.kkhura.simppler.sampleproject.ui.home.listner.AdapterOnItemClickable
import com.kkhura.simppler.sampleproject.ui.home.model.AlbumContainerModel
import com.kkhura.simppler.sampleproject.ui.home.model.Item
import com.kkhura.simppler.sampleproject.ui.home.model.Item_
import com.kkhura.simppler.sampleproject.ui.main.view.HomeActivityViewModel
import kotlinx.android.synthetic.main.fragment_saved_albums_list.*
import java.util.*

class SavedAlbumsListFragment() : BaseFragment(), AdapterOnItemClickable {
    private var isAllAlbumsAdded: Boolean = false
    private var offset: Int = 0
    private var totalCount: Int = 0
    private var list: List<Item> = ArrayList<Item>()
    private var adapter: AlbumsAdapter? = null

    private val homeActivityViewModel: HomeActivityViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[HomeActivityViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity.application as AppApplication).networkComponent.inject(this)
        return inflater.inflate(R.layout.fragment_saved_albums_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()

        tv_albums_not_found.setOnClickListener(View.OnClickListener {
            progress_bar.visibility = View.VISIBLE
            homeActivityViewModel.getAlbums(10, 0).observe(this, android.arch.lifecycle.Observer { albumContainerModel ->
                progress_bar.visibility = View.GONE
                if (albumContainerModel is AlbumContainerModel) {
                    renderAlbums(albumContainerModel)
                    tv_albums_not_found.visibility = View.GONE
                } else if (albumContainerModel is Error) {
                    tv_albums_not_found.visibility = View.VISIBLE
                    tv_albums_not_found.text = "Error"
                }
            })
        })
    }

    override val title: String
        get() = "Saved Albums"

    private fun setAdapter() {
        rv_saved_albums_list.layoutManager = LinearLayoutManager(context)
        adapter = AlbumsAdapter(activity, list, this)
        rv_saved_albums_list.setAdapter(adapter)
        offset = 0
    }

    fun renderAlbums(albumContainerModel: AlbumContainerModel) {
        rv_saved_albums_list.visibility = View.VISIBLE
        if (albumContainerModel.items.size > 0) {
            list = albumContainerModel.items
            totalCount = albumContainerModel.total
        }
        offset = list.size
        adapter?.notifyDataSetChanged()
        if (list.size == totalCount) {
            isAllAlbumsAdded = true
        }
    }

    override fun onItemClicked(v: View?, position: Int) {
        val albumDetailFragment = AlbumDetailFragment()
        val args = Bundle()
        args.putSerializable(ApplicationConstants.ALBUM_TITLE, title)
        args.putInt(ApplicationConstants.SELECTED_ALBUM_INDEX, position)
        args.putParcelableArrayList(ApplicationConstants.ALBUM, ArrayList<Item_>(list.get(position).album.tracks.items))
        args.putString(ALBUM_URL, list.get(position).album.images.get(0).url)
        albumDetailFragment.setArguments(args)
        add(albumDetailFragment)
    }
}