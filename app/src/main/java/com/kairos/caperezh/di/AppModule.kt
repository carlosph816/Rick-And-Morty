package com.kairos.caperezh.di

import com.kairos.caperezh.network.ApiService
import com.kairos.caperezh.network.BaseServiceObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(serviceObject: BaseServiceObject): ApiService =
        serviceObject.invoke().build().create(ApiService::class.java)

}