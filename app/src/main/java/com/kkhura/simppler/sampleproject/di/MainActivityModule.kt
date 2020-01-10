package com.kkhura.simppler.sampleproject.di

import com.kkhura.simppler.sampleproject.ui.home.view.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): HomeActivity
}
