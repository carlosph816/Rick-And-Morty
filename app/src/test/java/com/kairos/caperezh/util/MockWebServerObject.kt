package com.kairos.caperezh.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MockWebServerObject {

    val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    fun MockWebServer.getMockRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(this.url("/"))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
