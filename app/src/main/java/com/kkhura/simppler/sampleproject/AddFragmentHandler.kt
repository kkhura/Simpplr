package com.kkhura.simppler.sampleproject

import android.support.v4.app.FragmentManager

internal class AddFragmentHandler(private val fragmentManager: FragmentManager) {
    fun add(fragment: BaseFragment) { //don't add a fragment of the same type on top of itself.
        currentFragment?.let {
            val currentFragment: BaseFragment = it
            if (currentFragment.javaClass === fragment.javaClass) {
                return
            }
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_content, fragment, fragment.title)
        fragmentTransaction.addToBackStack(fragment.title)
        fragmentTransaction.commit()
    }

    val currentFragment: BaseFragment?
        get() {
            if (fragmentManager.backStackEntryCount == 0) {
                return null
            }
            val currentEntry = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
            val tag = currentEntry.name
            val fragment = fragmentManager.findFragmentByTag(tag)
            return fragment as BaseFragment
        }

}