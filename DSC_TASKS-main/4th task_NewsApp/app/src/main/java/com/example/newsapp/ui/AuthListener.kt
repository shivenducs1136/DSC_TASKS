package com.example.newsapp.ui

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}