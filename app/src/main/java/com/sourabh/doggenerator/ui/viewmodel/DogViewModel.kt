package com.sourabh.doggenerator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourabh.doggenerator.domain.model.DogImage
import com.sourabh.doggenerator.domain.repository.DogRepository
import com.sourabh.doggenerator.domain.usecase.GetRandomDogUseCase
import com.sourabh.doggenerator.domain.usecase.GetSavedDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val getRandomDogUseCase: GetRandomDogUseCase,
    private val getSavedDogsUseCase: GetSavedDogsUseCase,
    private val repository: DogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DogUiState>(DogUiState.Initial)
    val uiState: StateFlow<DogUiState> = _uiState.asStateFlow()

    val savedDogs = getSavedDogsUseCase()

    fun generateNewDog() {
        viewModelScope.launch {
            _uiState.value = DogUiState.Loading
            try {
                getRandomDogUseCase().fold(
                    onSuccess = { dogImage ->
                        _uiState.value = DogUiState.Success(dogImage)
                    },
                    onFailure = { error ->
                        Timber.e(error)
                        _uiState.value = DogUiState.Error(error.message ?: "Unknown error occurred")
                    }
                )
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = DogUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            repository.clearDogs()
        }
    }
}

sealed class DogUiState {
    data object Initial : DogUiState()
    data object Loading : DogUiState()
    data class Success(val dogImage: DogImage) : DogUiState()
    data class Error(val message: String) : DogUiState()
} 