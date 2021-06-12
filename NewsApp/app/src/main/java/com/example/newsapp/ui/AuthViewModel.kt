package com.example.newsapp.ui

import android.view.View
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {

    var email:String? = null
    var password:String? = null


    lateinit var authListener: AuthListener



    fun onLoginButtonClick(view: View){

        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Details")
            return
        }
        //Authenticated
        authListener?.onSuccess()

    }
}