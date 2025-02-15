package com.sourabh.doggenerator.domain.model

data class DogImage(
    val url: String,
    val timestamp: Long = System.currentTimeMillis()
) 