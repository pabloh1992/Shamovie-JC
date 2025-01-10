package com.pablodev.shamovie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pablodev.shamovie.screens.MainScreen
import com.pablodev.shamovie.screens.TutorialScreen

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.RootGraph
    ) {
        navigation<Route.RootGraph>(
            startDestination = Route.Tutorial
        ) {
            composable<Route.Tutorial>{
                TutorialScreen(navController)
            }

            composable<Route.Main> {
                MainScreen()
            }
        }
    }
}
