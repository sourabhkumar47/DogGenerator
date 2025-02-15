package com.sourabh.doggenerator.domain.usecase

import com.sourabh.doggenerator.domain.model.DogImage
import com.sourabh.doggenerator.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedDogsUseCase @Inject constructor(
    private val repository: DogRepository
) {
    operator fun invoke(): Flow<List<DogImage>> {
        return repository.getSavedDogs()
    }
} 