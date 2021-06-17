package com.example.retrofitapi

import retrofit2.http.GET

interface SimpleApi {

    @GET("posts")
    suspend fun getPost():Post

}