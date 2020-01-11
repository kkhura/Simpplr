package com.kkhura.simppler.sampleproject.ui.home.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import com.kkhura.simppler.sampleproject.AppApplication
import com.kkhura.simppler.sampleproject.BaseActivity
import com.kkhura.simppler.sampleproject.R
import com.kkhura.simppler.sampleproject.ui.home.adapter.NavigationDrawerAdapter
import com.kkhura.simppler.sampleproject.ui.home.fragment.SavedAlbumsListFragment
import com.kkhura.simppler.sampleproject.ui.home.listner.AdapterOnItemClickable
import com.kkhura.simppler.sampleproject.ui.home.model.UserModel
import com.kkhura.simppler.sampleproject.ui.main.view.HomeActivityViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), AdapterOnItemClickable, HasSupportFragmentInjector {
    private var drawerToggle: ActionBarDrawerToggle? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    public val homeActivityViewModel: HomeActivityViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[HomeActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        (application as AppApplication).networkComponent.inject(this)

        homeActivityViewModel.getUser().observe(this, Observer { userModel ->
            if (userModel is UserModel) {
                tv_user_name.text = userModel.display_name
                tv_user_email_address.text = userModel.email
            }
        })
        setupNavigationItems()
        setupDrawerAndToggle()
        showSavedAlbumsList()
    }

    override fun getDrawerToggle(): ActionBarDrawerToggle? {
        return drawerToggle
    }

    override fun getDrawer(): DrawerLayout {
        return main_drawer
    }

    private fun setupNavigationItems() {
        val navigationDrawerAdapter = NavigationDrawerAdapter(this, homeActivityViewModel.getNavigationList(this), this)
        rv_drawer_list.setLayoutManager(LinearLayoutManager(this))
        rv_drawer_list.setAdapter(navigationDrawerAdapter)
    }

    private fun setupDrawerAndToggle() {
        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle = object : android.support.v7.app.ActionBarDrawerToggle(this, main_drawer, main_toolbar,
                0, 0) {
            override fun onDrawerOpened(drawerView: android.view.View) {
                super.onDrawerOpened(drawerView)
                setDrawerIndicatorEnabled(true)
            }

            override fun onDrawerClosed(drawerView: android.view.View) {
                super.onDrawerClosed(drawerView)
            }
        }
        drawerToggle?.let { drawerToggle ->
            main_drawer.addDrawerListener(drawerToggle)
            drawerToggle.syncState()
        }
    }

    private fun showSavedAlbumsList() {
        fragmentManager?.let { fragmentManager ->
            if (fragmentManager.backStackEntryCount > 1) {
                fragmentManager.popBackStack()
            } else {
                add(SavedAlbumsListFragment())
            }
        }
    }

    override fun onItemClicked(v: android.view.View?, position: Int) {
        showSavedAlbumsList()
        main_drawer.closeDrawers()
        drawerToggle?.syncState()
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}