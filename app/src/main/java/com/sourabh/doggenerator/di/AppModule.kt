package com.sourabh.doggenerator.di

import android.content.Context
import com.sourabh.doggenerator.data.local.DogCacheManager
import com.sourabh.doggenerator.data.remote.api.DogApiService
import com.sourabh.doggenerator.data.repository.DogRepositoryImpl
import com.sourabh.doggenerator.domain.repository.DogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDogApiService(): DogApiService {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDogCacheManager(
        @ApplicationContext context: Context
    ): DogCacheManager {
        return DogCacheManager(context)
    }

    @Provides
    @Singleton
    fun provideDogRepository(
        api: DogApiService,
        cacheManager: DogCacheManager
    ): DogRepository {
        return DogRepositoryImpl(api, cacheManager)
    }
} 