package com.pablodev.shamovie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pablodev.shamovie.screens.MainScreen
import com.pablodev.shamovie.screens.TutorialScreen

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        route = Screen.RootGraph.route,
        startDestination = Screen.Tutorial.route
    ) {
        composable(route = Screen.Tutorial.route){
            TutorialScreen(navController)
        }

        composable(route = Screen.Main.route) {
            MainScreen()
        }
    }
}