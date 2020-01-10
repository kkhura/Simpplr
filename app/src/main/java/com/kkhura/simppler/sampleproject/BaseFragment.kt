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
        if (activity != null) {
            fragmentHandler = AddFragmentHandler(activity!!.supportFragmentManager)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (activity != null) activity!!.title = title
    }

    abstract var title: String
    protected fun add(fragment: BaseFragment) {
        fragmentHandler?.let { it.add(fragment) }
    }
}