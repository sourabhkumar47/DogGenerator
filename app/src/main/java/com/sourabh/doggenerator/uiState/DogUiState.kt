package com.sourabh.doggenerator.uiState

import com.sourabh.doggenerator.domain.model.DogImage

sealed class DogUiState {
    data object Initial : DogUiState()
    data object Loading : DogUiState()
    data class Success(val dogImage: DogImage) : DogUiState()
    data class Error(val message: String) : DogUiState()
} 