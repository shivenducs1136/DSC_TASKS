package com.example.mvvmsampleapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.utils.toast

class LoginActivity : AppCompatActivity(), AuthListener{


    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = findViewById(R.id.progress_bar)

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val viewModel= ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel= viewModel
        viewModel.authListener = this


    }

    override fun onStarted() {
//    toast("Login Started")
        progressBar?.setVisibility(View.VISIBLE)
    }

    override fun onSuccess() {
        toast("Login Success")

    }

    override fun onFailure(message: String) {
        toast(message)

    }
}