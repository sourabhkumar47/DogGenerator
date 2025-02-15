package com.sourabh.doggenerator.ui.navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object Generate : Screens("generate")
    data object Gallery : Screens("gallery")
}