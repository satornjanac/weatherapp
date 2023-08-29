package com.satornjanac.weatherforecastapp.networking

import okhttp3.Interceptor
import okhttp3.Response

class HeaderAccept : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Accept", "application/json")
        return chain.proceed(builder.build())
    }
}

class HeaderContentType : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Content-Type", "application/json")
        return chain.proceed(builder.build())
    }
}