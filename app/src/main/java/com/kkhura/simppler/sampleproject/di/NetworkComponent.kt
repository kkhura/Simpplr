package com.kkhura.simppler.sampleproject.di

import com.kkhura.simppler.sampleproject.BaseActivity
import com.kkhura.simppler.sampleproject.BaseFragment
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, AndroidInjectionModule::class])
interface NetworkComponent {
    fun inject(activity: BaseActivity)
}