package com.example.retrofitapi

class Repository {

    suspend fun getPost(): Post{
        return RetrofitInstance.api.getPost()
    }

}