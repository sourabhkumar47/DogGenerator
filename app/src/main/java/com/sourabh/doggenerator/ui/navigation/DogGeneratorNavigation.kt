package com.sourabh.doggenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sourabh.doggenerator.ui.screens.gallery.GalleryScreen
import com.sourabh.doggenerator.ui.screens.generate.GenerateScreen
import com.sourabh.doggenerator.ui.screens.home.HomeScreen

@Composable
fun DogGeneratorNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.Generate.route) {
            GenerateScreen()
        }
        composable(Screens.Gallery.route) {
            GalleryScreen()
        }
    }
} 