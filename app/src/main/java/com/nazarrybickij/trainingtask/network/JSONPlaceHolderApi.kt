package com.nazarrybickij.trainingtask.network

import com.nazarrybickij.trainingtask.entities.UserEntity
import com.nazarrybickij.trainingtask.entities.UsersPageEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JSONPlaceHolderApi {
    @GET("users/{id}")
    fun getUserById(@Path("id") id:Int): Call<UserEntity?>?
    @GET("users")
    fun getPageUsers(@Query("page") page:Int): Call<UsersPageEntity?>?
}