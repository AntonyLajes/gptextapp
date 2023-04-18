package com.nomargin.gptextapp.ui.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance private constructor() {

    companion object {
        private lateinit var INSTANCE: Retrofit
        private val client = OkHttpClient.Builder().apply {
            addInterceptor(MyInterceptor())
        }.build()

        fun getRetrofitInstance(path: String): Retrofit {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(path)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }
    }

}