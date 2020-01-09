package com.kkhura.simppler.sampleproject.ui.home.repo

import android.arch.lifecycle.MutableLiveData
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.shareddata.SimpplerServices
import com.kkhura.simppler.sampleproject.ui.home.model.Response
import com.kkhura.simppler.sampleproject.ui.home.model.UserModel
import com.kkhura.simppler.sampleproject.ui.home.model.Error
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserRepository @Inject constructor(private val api: SimpplerServices) {

    private val mCompositeDisposable = CompositeDisposable()

    private val progress = MutableLiveData<Boolean>()

    fun getProgress() = progress

    fun getCompositeDisposable() = mCompositeDisposable

    fun getUser(): MutableLiveData<Response> {
        val data = MutableLiveData<Response>()
        api.fetchUserModel("Bearer "+ApplicationConstants.token)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe { progress.postValue(true) }
                ?.subscribe(
                         {
                            progress.postValue(false)
//                            data.value = UserModel("1", it?.userName.toString())
                        },
                        {
                            progress.postValue(false)
                            data.value = Error("User not found")
                        }
                )?.addTo(mCompositeDisposable)
        return data
    }
}