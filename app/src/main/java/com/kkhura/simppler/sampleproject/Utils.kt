package com.kkhura.simppler.sampleproject

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity

object Utils {
    fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}