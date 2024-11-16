package com.example.myapplication

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feteh {
    fun fetch(callback: fetchCallBackData):String{
        var data = ""
        RetrofitClient.apiService.getFeeds().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>){
                data = "on response 1"
                if (response.isSuccessful) {
                    // Get the response body which is a raw String
                    val responseData = response.body()
                    if (responseData != null) {
//                        val latestFeedField1 = responseData.feeds.firstOrNull()?.field1 ?: "No data"
                        val feedList = responseData.feeds


                        callback.onSucess(feedList)
                    } else {
                       data = "Error :Empty response body."
                    }
                } else {
                  data = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle failure
                data = "Error: Call FAil"

            }
        })
        return data
    }

}