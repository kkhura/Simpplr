package com.kkhura.simppler.sampleproject.shareddata

import com.kkhura.simppler.sampleproject.ui.home.model.AlbumContainerModel
import com.kkhura.simppler.sampleproject.ui.home.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SimpplerServices {
    @GET("/v1/me")
    fun fetchUserModel(@Header("Authorization") authToken: String?): Observable<UserModel?>?

    @GET("/v1/me/albums?")
    fun fetchAlbums(@Header("Authorization") authToken: String?,
                    @Query("limit") limit: Int,
                    @Query("offset") offset: Int): Observable<AlbumContainerModel?>?
}
