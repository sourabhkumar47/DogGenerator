package com.sourabh.doggenerator.domain.usecase

import com.sourabh.doggenerator.domain.model.DogImage
import com.sourabh.doggenerator.domain.repository.DogRepository
import javax.inject.Inject

class GetRandomDogUseCase @Inject constructor(
    private val repository: DogRepository
) {
    suspend operator fun invoke(): Result<DogImage> {
        return repository.getRandomDog()
    }
} 