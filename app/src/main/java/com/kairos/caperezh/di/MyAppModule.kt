package com.kairos.caperezh.di

import com.kairos.caperezh.data.RemoteDataSource
import com.kairos.caperezh.data.RemoteDataSourceImpl
import com.kairos.caperezh.domain.HomeRepository
import com.kairos.caperezh.data.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MyAppModule {

    @Binds
    abstract fun providePaymentCardRepository(
        implementation: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun provideRemoteDataSource(
        implementation: RemoteDataSourceImpl
    ): RemoteDataSource

}