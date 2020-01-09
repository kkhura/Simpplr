package com.kkhura.simppler.sampleproject.ui.main.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.Toast
import com.kkhura.simppler.sampleproject.AppApplication
import com.kkhura.simppler.sampleproject.BaseActivity
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.shareddata.ApplicationConstants
import com.kkhura.simppler.sampleproject.ui.home.view.HomeActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val loginviewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as AppApplication).networkComponent.inject(this)
        loginButton.setOnClickListener(View.OnClickListener { loginviewModel.onLoginClick(this) })
    }

    override fun getDrawerToggle(): ActionBarDrawerToggle? {
        return null
    }

    override fun getDrawer(): DrawerLayout? {
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == ApplicationConstants.REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthenticationResponse.Type.TOKEN -> {
                    ApplicationConstants.token = response.getAccessToken()
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    finish()
                }
                AuthenticationResponse.Type.ERROR -> {
                    Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }
    }
}
