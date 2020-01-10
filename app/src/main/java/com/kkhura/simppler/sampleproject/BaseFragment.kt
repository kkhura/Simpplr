package com.kkhura.simppler.sampleproject

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var fragmentHandler: AddFragmentHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.let { activity ->
            fragmentHandler = AddFragmentHandler(activity.supportFragmentManager)
            (activity as BaseActivity).syncDrawerToggleState()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        activity?.let { activity ->
            activity.title = title
        }
    }

    abstract var title: String
    protected fun add(fragment: BaseFragment) {
        fragmentHandler?.add(fragment)
    }
}