package com.example.unsplashandroid.Api

import UnsPlashApi
import com.example.unsplashandroid.const.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: UnsPlashApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsPlashApi::class.java)
    }
}