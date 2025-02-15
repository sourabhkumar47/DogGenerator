package com.sourabh.doggenerator.data

import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogApiResponse
} 