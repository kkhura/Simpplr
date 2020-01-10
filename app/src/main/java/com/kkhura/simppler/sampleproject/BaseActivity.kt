package com.kkhura.simppler.sampleproject

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.DrawerLayout.DrawerListener
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kkhura.simppler.sampleproject.ui.home.fragment.SavedAlbumsListFragment
import com.kkhura.simppler.sampleproject.ui.home.listner.BackButtonSupportFragment
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected var fragmentManager: FragmentManager? = null
    private var fragmentHandler: AddFragmentHandler? = null

    private var backStackListener: () -> Unit = {
        backStackListener = this::onBackStackChangedEvent;
    }

    private fun onBackStackChangedEvent() {
        syncDrawerToggleState()
    }

    fun syncDrawerToggleState() {
        val drawerToggle = getDrawerToggle() ?: return
        fragmentManager?.let { fragmentManager ->
            if (fragmentManager.backStackEntryCount > 1) {
                drawerToggle.isDrawerIndicatorEnabled = false
                drawerToggle.toolbarNavigationClickListener = View.OnClickListener {
                    syncDrawerToggleState()
                    fragmentManager.popBackStack()
                }
            } else {
                drawerToggle.isDrawerIndicatorEnabled = true
                drawerToggle.toolbarNavigationClickListener = drawerToggle.toolbarNavigationClickListener
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        fragmentManager?.let {
            fragmentHandler = AddFragmentHandler(it)
            it.addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener() {
                syncDrawerToggleState()
            })
        }
    }

    override fun onDestroy() {
        fragmentManager?.removeOnBackStackChangedListener(backStackListener)
        fragmentManager = null
        super.onDestroy()
    }

    fun add(fragment: BaseFragment) {
        fragmentHandler?.add(fragment)
    }

    override fun onBackPressed() {
        if (sendBackPressToDrawer()) {
            return
        }
        if (sendBackPressToFragmentOnTop()) {
            return
        }
        super.onBackPressed()
        if (fragmentManager?.backStackEntryCount == 0) {
            finish()
        }
    }

    open fun sendBackPressToDrawer(): Boolean {
        val drawer = getDrawer()
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    open fun sendBackPressToFragmentOnTop(): Boolean {
        fragmentHandler?.currentFragment?.let { fragmentOnTop ->
            return fragmentOnTop is BackButtonSupportFragment &&
                    (fragmentOnTop as BackButtonSupportFragment).onBackPressed()
        }
        return false
    }

    protected abstract fun getDrawerToggle(): ActionBarDrawerToggle?

    protected abstract fun getDrawer(): DrawerLayout?
}

