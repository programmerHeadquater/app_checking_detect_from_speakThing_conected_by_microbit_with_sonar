package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

//    private const val BASE_URL = "https://your-api-url.com/"  // Replace with your actual API base URL
    //api.thingspeak.com/channels/2738584/feeds.json?results=2

    private const val BASE_URL = "https://api.thingspeak.com/"  // Replace with your actual API base URL

    private var retrofit: Retrofit? = null

    // Singleton pattern to get Retrofit instance
    val apiService: MemberApiService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Set the base URL
                    .addConverterFactory(GsonConverterFactory.create())  // Use Gson for JSON conversion
                    .build()
            }
            return retrofit!!.create(MemberApiService::class.java)  // Return the API service instance
        }
}
