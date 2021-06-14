package com.example.newsapp

import com.example.newsapp.api.NewsApiJSON
import retrofit2.http.GET

interface APIRequest {
    @GET("/v2/top-headlines?country=in&apiKey=a2dcdde82b024544b87dcdfc364f3658")
    suspend fun getNews():NewsApiJSON
}