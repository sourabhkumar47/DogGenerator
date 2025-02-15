package com.sourabh.doggenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sourabh.doggenerator.ui.screens.gallery.GalleryScreen
import com.sourabh.doggenerator.ui.screens.generate.GenerateScreen
import com.sourabh.doggenerator.ui.screens.home.HomeScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Generate : Screen("generate")
    data object Gallery : Screen("gallery")
}

@Composable
fun DogGeneratorNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Generate.route) {
            GenerateScreen(navController = navController)
        }
        composable(Screen.Gallery.route) {
            GalleryScreen(navController = navController)
        }
    }
} 