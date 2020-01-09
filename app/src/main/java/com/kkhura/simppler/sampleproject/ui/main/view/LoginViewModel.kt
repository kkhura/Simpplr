package com.kkhura.simppler.sampleproject.ui.main.view

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Intent
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    fun onLoginClick(activity: Activity){
        val builder = AuthenticationRequest.Builder(
                ApplicationConstants.CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                ApplicationConstants.REDIRECT_URI
        )
        builder.setScopes(ApplicationConstants.REQUESTED_SCOPE)
        val request = builder.build()
        AuthenticationClient.openLoginActivity(activity, ApplicationConstants.REQUEST_CODE, request)
    }
}
