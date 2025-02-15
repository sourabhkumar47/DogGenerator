package com.sourabh.doggenerator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DogGeneratorApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
} 