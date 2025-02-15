package com.sourabh.doggenerator.domain.repository

import com.sourabh.doggenerator.domain.model.DogImage
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    suspend fun getRandomDog(): Result<DogImage>
    fun getSavedDogs(): Flow<List<DogImage>>
    suspend fun saveDog(dogImage: DogImage)
    suspend fun clearDogs()
} 