package com.kkhura.simppler.sampleproject.ui.home.listner

interface BackButtonSupportFragment {
    // return true if your fragment has consumed the back press event, false if you don't care about it
    fun onBackPressed(): Boolean
}