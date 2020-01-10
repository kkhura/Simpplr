package com.kkhura.simppler.sampleproject.di

import com.kkhura.simppler.sampleproject.ui.home.fragment.AlbumDetailFragment
import com.kkhura.simppler.sampleproject.ui.home.fragment.SavedAlbumsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSavedAlbumsListFragment(): SavedAlbumsListFragment

    @ContributesAndroidInjector
    abstract fun contributeAlbumDetailFragment(): AlbumDetailFragment
}