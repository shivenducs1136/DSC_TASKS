package com.example.newsapp

class User {

    public lateinit var fullname:String
    public lateinit var age:String
    public lateinit var phonenum:String
    public lateinit var address:String
    public lateinit var bio:String
    public lateinit var email:String

    constructor(
        fullname: String,
        age: String,
        phonenum: String,
        address: String,
        bio: String,
        email: String
    ) {
        this.fullname = fullname
        this.age = age
        this.phonenum = phonenum
        this.address = address
        this.bio = bio
        this.email = email
    }
}