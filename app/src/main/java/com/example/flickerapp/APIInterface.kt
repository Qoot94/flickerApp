package com.example.flickerapp

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Url

interface APIInterface {
    @GET
    fun getApi(@Url url: String):retrofit2.Call<PhotoObject>
}