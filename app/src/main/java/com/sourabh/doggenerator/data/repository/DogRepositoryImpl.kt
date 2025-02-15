package com.sourabh.doggenerator.data.repository

import com.sourabh.doggenerator.data.remote.api.DogApiService
import com.sourabh.doggenerator.domain.model.DogImage
import com.sourabh.doggenerator.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val api: DogApiService,
    private val cacheManager: com.sourabh.doggenerator.data.local.DogCacheManager
) : DogRepository {

    override suspend fun getRandomDog(): Result<DogImage> {
        return try {
            val response = api.getRandomDog()
            if (response.status == "success") {
                val dogImage = DogImage(response.message)
                saveDog(dogImage)
                Result.success(dogImage)
            } else {
                Result.failure(Exception("Failed to fetch dog image"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getSavedDogs(): Flow<List<DogImage>> {
        return cacheManager.getDogs().map { urls ->
            urls.map { DogImage(it) }
        }
    }

    override suspend fun saveDog(dogImage: DogImage) {
        cacheManager.addDog(dogImage.url)
    }

    override suspend fun clearDogs() {
        cacheManager.clearCache()
    }
} 