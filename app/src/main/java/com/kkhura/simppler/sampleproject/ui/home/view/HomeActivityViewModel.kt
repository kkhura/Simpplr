package com.kkhura.simppler.sampleproject.ui.main.view


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.shareddata.SimpplerServices
import com.kkhura.simppler.sampleproject.ui.home.model.AlbumContainerModel
import com.kkhura.simppler.sampleproject.ui.home.model.Error
import com.kkhura.simppler.sampleproject.ui.home.model.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeActivityViewModel @Inject constructor(private val api: SimpplerServices) : ViewModel() {
    private val mCompositeDisposable = CompositeDisposable()
    lateinit var albumContainerModel: AlbumContainerModel

    fun getUser(): MutableLiveData<Response> {
        val data = MutableLiveData<Response>()
        api.fetchUserModel("Bearer " + ApplicationConstants.token)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            data.value = it
                        },
                        {
                            data.value = Error("User not found")
                        }
                )?.addTo(mCompositeDisposable)
        return data
    }

    fun getAlbums(limit: Int, offset: Int): MutableLiveData<Response> {
        val data = MutableLiveData<Response>()
        api.fetchAlbums("Bearer " + ApplicationConstants.token, limit, offset)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            data.value = it
                            if (it != null) {
                                albumContainerModel = it
                            }
                        },
                        {
                            data.value = Error("User not found")
                        }
                )?.addTo(mCompositeDisposable)
        return data
    }


    fun getNavigationList(context: Context): Array<String> {
        return context.resources.getStringArray(R.array.nav_drawer_items)
    }
}
