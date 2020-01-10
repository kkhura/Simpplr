package com.kkhura.simppler.sampleproject.ui.home.repo

import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.shareddata.SimpplerServices
import com.kkhura.simppler.sampleproject.ui.home.model.AlbumContainerModel
import com.kkhura.simppler.sampleproject.ui.home.model.UserModel
import io.reactivex.Observable
import retrofit2.Call
import java.io.InputStream
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeActivityRepo @Inject constructor(private val api: SimpplerServices) {
    fun getUserDataRepo(): Observable<UserModel?>? {
//        if (MockData.isMockData) {
        return api.fetchUserModel("Bearer " + ApplicationConstants.token)
//        } else {
//            Observable.fromCallable(Callable<UserModel>() {
//            })
//        }
    }

    fun fetchAlbumDataRepo(limit: Int, offset: Int): Observable<AlbumContainerModel?>? {
        return api.fetchAlbums("Bearer " + ApplicationConstants.token, limit, offset)
    }

//    fun readJSONFromAsset(): String? {
//        var json: String? = null
//        try {
//            val inputStream: InputStream = applicationContext.assets.open("yourFile.json")
//            json = inputStream.bufferedReader().use { it.readText() }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            return null
//        }
//        return json
//    }
}