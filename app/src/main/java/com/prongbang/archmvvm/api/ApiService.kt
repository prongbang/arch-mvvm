package com.prongbang.archmvvm.api

import com.prongbang.archmvvm.vo.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun getUser(): Call<List<User>>

}