package com.ponomar.shoper.ui.auth

interface FragmentCallBacks {
    fun onFragment1NextClick(firstName:String) //TODO:maybe refactor
    fun onFragment2NextClick(firstName: String,phone:String)
    fun onFragment3NextClick(phone:String)
    fun onFragment4NextClick()

    fun onFragment2BackClick()
    fun onFragment3BackClick()
    fun onFragment4BackClick()
}