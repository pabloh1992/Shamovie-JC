package com.pablodev.shamovie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pablodev.shamovie.media.presentation.discover.DiscoverState
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.media.presentation.list.MediaListViewModel
import com.pablodev.shamovie.screens.DetailScreen
import com.pablodev.shamovie.screens.DiscoverScreen
import com.pablodev.shamovie.screens.MoviesScreen
import com.pablodev.shamovie.screens.TvShowsScreen
import org.koin.compose.viewmodel.koinViewModel

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
            MoviesScreen(
                viewModel = koinViewModel<MediaListViewModel>(),
                navController = navController
            )
        }

        composable(route = Screen.Discover.route){
            DiscoverScreen(
                viewModel = koinViewModel<DiscoverViewModel>(),
                navController = navController
            )
        }

        composable(route = Screen.TvShowList.route){
            TvShowsScreen()
        }

        composable(route = Screen.Details.route){
            DetailScreen()
        }
    }
}