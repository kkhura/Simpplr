package com.kkhura.simppler.sampleproject.ui.home.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import java.util.*

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {
    private var albumList: List<Item> = ArrayList<Item>()
    val list: List<Any>
        get() = albumList

    fun setAlbumList(list: List<Item>) {
        albumList = list
    }
}