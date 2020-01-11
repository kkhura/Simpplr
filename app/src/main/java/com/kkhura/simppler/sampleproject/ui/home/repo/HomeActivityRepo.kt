package com.kkhura.simppler.sampleproject.ui.home.repo

import android.content.Context
import com.google.gson.Gson
import com.kkhura.simppler.sampleproject.di.ContextModule
import com.kkhura.simppler.sampleproject.di.DaggerAppComponent
import com.kkhura.simppler.sampleproject.di.NetworkModule
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.shareddata.SimpplerServices
import com.kkhura.simppler.sampleproject.ui.home.model.AlbumContainerModel
import com.kkhura.simppler.sampleproject.ui.home.model.UserModel
import io.reactivex.Observable
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named

class HomeActivityRepo @Inject constructor(private val api: SimpplerServices) {
    fun getUserDataRepo(): Observable<UserModel?>? {
        if (!MockData.isMockData) {
            return api.fetchUserModel("Bearer " + ApplicationConstants.token)
        } else {
            val userModel = Gson().fromJson(readJSONFromAsset("userDetails.json"), UserModel::class.java)
            return Observable.fromCallable { userModel }
        }
    }

    fun fetchAlbumDataRepo(limit: Int, offset: Int): Observable<AlbumContainerModel?>? {
        if (!MockData.isMockData) {
            return api.fetchAlbums("Bearer " + ApplicationConstants.token, limit, offset)
        } else {
            val albumContainerModel = Gson().fromJson(readJSONFromAsset("albumDetail.json"), AlbumContainerModel::class.java)
            return Observable.fromCallable { albumContainerModel }
        }
    }

    fun readJSONFromAsset(file: String): String? {
        var json: String? = null
        try {
            val inputStream = FileInputStream("../app/src/main/assets/" + file)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}