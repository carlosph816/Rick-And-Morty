package com.kairos.caperezh.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BaseServiceObject @Inject constructor() {

    operator fun invoke(): Retrofit.Builder {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.interceptors().clear()
        okHttpClientBuilder
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)

        val client: OkHttpClient = okHttpClientBuilder.build()

        return  Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }
}