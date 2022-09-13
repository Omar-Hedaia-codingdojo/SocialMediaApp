package com.hedaia.socialmediaappomar

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {

//    private var retrofit: ApiCall? = null

    fun getAPIClient(): ApiCall {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        // New Code
        val gsonBuilder = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://apidojo.pythonanywhere.com")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder)) // New Code
            .client(okHttpClient)
            .build()
            .create(ApiCall::class.java)
    }







}