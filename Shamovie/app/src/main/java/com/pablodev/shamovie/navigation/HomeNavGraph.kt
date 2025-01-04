package com.pablodev.shamovie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pablodev.shamovie.screens.DetailScreen
import com.pablodev.shamovie.screens.DiscoverScreen
import com.pablodev.shamovie.screens.MoviesScreen
import com.pablodev.shamovie.screens.TvShowsScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Screen.MainGraph.route,
        startDestination = Screen.Discover.route
    ) {
        composable(route = Screen.MovieList.route){
            MoviesScreen(navController)
        }

        composable(route = Screen.Discover.route){
            DiscoverScreen()
        }

        composable(route = Screen.TvShowList.route){
            TvShowsScreen()
        }

        composable(route = Screen.Details.route){
            DetailScreen()
        }
    }
}