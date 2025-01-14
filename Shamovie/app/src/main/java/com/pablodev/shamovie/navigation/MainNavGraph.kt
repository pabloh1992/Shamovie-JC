package com.pablodev.shamovie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pablodev.shamovie.media.presentation.detail.DetailViewModel
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.media.presentation.list.MediaListViewModel
import com.pablodev.shamovie.screens.DetailScreen
import com.pablodev.shamovie.screens.DiscoverScreen
import com.pablodev.shamovie.screens.MoviesScreen
import com.pablodev.shamovie.screens.TvShowsScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun MainNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Route.MainGraph
    ) {

        navigation<Route.MainGraph>(
            startDestination = Route.Discover
        ) {

            composable<Route.MovieList>{
                MoviesScreen(
                    viewModel = koinViewModel<MediaListViewModel>
                        (named("movie")),
                    navController = navController,
                    paddingValues = paddingValues
                )
            }

            composable<Route.Discover>{
                DiscoverScreen(
                    viewModel = koinViewModel<DiscoverViewModel>(),
                    navController = navController,
                    paddingValues = paddingValues
                )
            }

            composable<Route.TvShowList>{
                TvShowsScreen(
                    viewModel = koinViewModel<MediaListViewModel>
                        (named("tv")),
                    navController = navController,
                    paddingValues = paddingValues
                )
            }

            composable<Route.Details>{ entry ->
                //val args = entry.toRoute<Route.Details>()
                val query = entry.arguments?.getString("query")
                val id = entry.arguments?.getString("mediaId")
                val isMovie = entry.arguments?.getBoolean("isMovie")

                    id?.let {
                        isMovie?.let {
                            DetailScreen(
                                viewModel = koinViewModel<DetailViewModel>(),
                                navController = navController,
                                query = query,
                                id = id,
                                isMovie = isMovie,
                                paddingValues = paddingValues
                            )
                        }
                    }
                }
            }
        }

}
