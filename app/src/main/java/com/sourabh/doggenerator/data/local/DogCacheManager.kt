package com.sourabh.doggenerator.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "dog_cache")
private val CACHE_KEY = stringPreferencesKey("dog_urls")

class DogCacheManager @Inject constructor(
    private val context: Context
) {
    private val gson = Gson()
    private val maxCacheSize = 20

    suspend fun addDog(url: String) {
        context.dataStore.edit { preferences ->
            val currentCache = getCacheFromPreferences(preferences[CACHE_KEY])
            val updatedCache = (listOf(url) + currentCache).distinct().take(maxCacheSize)
            preferences[CACHE_KEY] = gson.toJson(updatedCache)
        }
    }

    fun getDogs(): Flow<List<String>> {
        return context.dataStore.data.map { preferences ->
            getCacheFromPreferences(preferences[CACHE_KEY])
        }
    }

    suspend fun clearCache() {
        context.dataStore.edit { preferences ->
            preferences[CACHE_KEY] = gson.toJson(emptyList<String>())
        }
    }

    private fun getCacheFromPreferences(jsonCache: String?): List<String> {
        if (jsonCache == null) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return try {
            gson.fromJson(jsonCache, type)
        } catch (e: Exception) {
            emptyList()
        }
    }
} 