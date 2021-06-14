package com.example.newsapp.ui

interface RegAuthlistener {
    fun on_Started()
    fun on_Success()
    fun on_Failure(message: String)
}