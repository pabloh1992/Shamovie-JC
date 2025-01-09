package com.pablodev.shamovie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.media.presentation.list.MediaListViewModel
import com.pablodev.shamovie.screens.DetailScreen
import com.pablodev.shamovie.screens.DiscoverScreen
import com.pablodev.shamovie.screens.MoviesScreen
import com.pablodev.shamovie.screens.TvShowsScreen
import io.ktor.http.parametersOf
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named
import org.koin.viewmodel.getViewModelKey

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        route = Screen.MainGraph.route,
        startDestination = Screen.Discover.route
    ) {
        composable(route = Screen.MovieList.route){
            MoviesScreen(
                viewModel = koinViewModel<MediaListViewModel>
                    (named("movie")),
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable(route = Screen.Discover.route){
            DiscoverScreen(
                viewModel = koinViewModel<DiscoverViewModel>(),
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable(route = Screen.TvShowList.route){
            TvShowsScreen(
                viewModel = koinViewModel<MediaListViewModel>
                    (named("tv")),
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable(route = Screen.Details.route){
            DetailScreen()
        }
    }
}