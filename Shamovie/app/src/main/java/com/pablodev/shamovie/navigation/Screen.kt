package com.pablodev.shamovie.navigation

sealed class Screen(val route: String) {

    data object Tutorial: Screen("tutorial")
    data object Main: Screen("main")
    data object Discover: Screen("discover")
    data object MovieList: Screen("movies")
    data object TvShowList: Screen("tvshows")
    data object Details: Screen("details")

    // Graph Routes
    data object RootGraph: Screen("ROOT_GRAPH")
    data object MainGraph: Screen("MAIN_GRAPH")
}