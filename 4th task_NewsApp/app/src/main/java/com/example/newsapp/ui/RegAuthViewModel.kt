package com.example.newsapp.ui

import android.view.View
import androidx.lifecycle.ViewModel

class RegAuthViewModel: ViewModel() {
    var email:String? = null
    var name:String? = null
    var password:String? = null
    var cnfpassword:String? = null
    var age:String? = null
    var phonenum:String? = null
    var address:String? = null
    var bio:String? = null

     lateinit var regauthListener:RegAuthlistener


    fun onRegisterButtonClick(view: View){

        regauthListener?.on_Started()
}

}


