package com.kkhura.simppler.sampleproject.ui.main.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.Toast
import com.kkhura.simppler.sampleproject.AppApplication
import com.kkhura.simppler.sampleproject.BaseActivity
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.Utils.verifyAvailableNetwork
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.ui.home.view.HomeActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as AppApplication).networkComponent.inject(this)
        loginButton.setOnClickListener(View.OnClickListener { onLoginClick(this) })
    }

    fun onLoginClick(activity: Activity) {
        if (verifyAvailableNetwork(this)) {
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

    override fun getDrawerToggle(): ActionBarDrawerToggle? = null

    override fun getDrawer(): DrawerLayout? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == ApplicationConstants.REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthenticationResponse.Type.TOKEN -> {
                    ApplicationConstants.token = response.getAccessToken()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
                else -> {
                    Toast.makeText(this@LoginActivity, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
