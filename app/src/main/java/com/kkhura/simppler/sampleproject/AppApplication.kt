package com.kkhura.simppler.sampleproject

import android.app.Application
import com.kkhura.simppler.sampleproject.di.*

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