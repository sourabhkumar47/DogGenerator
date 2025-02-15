package com.sourabh.doggenerator.data.remote.api

import com.sourabh.doggenerator.data.remote.dto.DogApiResponse
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogApiResponse
} 