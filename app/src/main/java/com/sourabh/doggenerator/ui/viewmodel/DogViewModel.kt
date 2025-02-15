package com.sourabh.doggenerator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sourabh.doggenerator.data.DogApiService
import com.sourabh.doggenerator.data.DogCacheManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogViewModel(application: Application) : AndroidViewModel(application) {
    private val cacheManager = DogCacheManager(application)
    private val apiService = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DogApiService::class.java)

    private val _currentDogImage = MutableStateFlow<String?>(null)
    val currentDogImage: StateFlow<String?> = _currentDogImage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val savedDogs = cacheManager.getDogs()

    fun generateNewDog() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val response = apiService.getRandomDog()
                if (response.status == "success") {
                    _currentDogImage.value = response.message
                    cacheManager.addDog(response.message)
                } else {
                    _error.value = "Failed to generate dog image"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            cacheManager.clearCache()
        }
    }
} 