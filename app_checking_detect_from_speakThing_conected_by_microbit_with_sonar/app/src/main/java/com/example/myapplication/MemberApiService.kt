package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberApiService {

    // GET request: Fetch member details by ID


    @GET("channels/2738584/fields/1.json?results=2")
    fun getFeeds(): Call<ApiResponse>

}

