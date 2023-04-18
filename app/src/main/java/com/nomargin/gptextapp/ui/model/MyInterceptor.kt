package com.nomargin.gptextapp.ui.model

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val proceed = chain.request()
            .newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "7ea476117fmsh8f9bc9c1d47b127p109bdcjsne35f515fc219")
            .addHeader("X-RapidAPI-Host", "chatgpt-api7.p.rapidapi.com")
            .build()
        return chain.proceed(proceed)
    }
}