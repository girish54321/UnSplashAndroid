package com.example.unsplashandroid.Api

import UnsPlashApi
import android.content.Context
import android.util.Log
import com.example.unsplashandroid.const.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
object RetrofitInstance {
    fun getApi(context: Context): UnsPlashApi {
        val sharedPref = context.getSharedPreferences(Constants.TOKAN, Context.MODE_PRIVATE)
        val token = sharedPref.getString(Constants.TOKAN, "")
        Log.e("Bearer","Bearer $token")
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsPlashApi::class.java)
    }
}
