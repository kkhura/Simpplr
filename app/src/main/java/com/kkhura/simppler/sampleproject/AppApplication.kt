package com.kkhura.simppler.sampleproject

import android.app.Application
import com.kkhura.simppler.sampleproject.di.DaggerNetworkComponent
import com.kkhura.simppler.sampleproject.di.FragmentBuildersModule
import com.kkhura.simppler.sampleproject.di.NetworkComponent
import com.kkhura.simppler.sampleproject.di.NetworkModule

class AppApplication : Application() {

    lateinit var networkComponent: NetworkComponent

    override fun onCreate() {
        super.onCreate()
        networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(NetworkModule)
                .build()
    }

}