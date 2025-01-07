package com.pablodev.shamovie.navigation

import com.pablodev.shamovie.R

sealed class BottomNavItems(
    val title : String,
    val route : String,
    val iconResource: Int,
){
    data object Movies : BottomNavItems(
        title = "Movies",
        route = Screen.MovieList.route,
        iconResource = R.drawable.ic_movie,
    )

    data object Discover : BottomNavItems(
        title = "Discover",
        route = Screen.Discover.route,
        iconResource = R.drawable.ic_mic
    )

    data object TvShows : BottomNavItems(
        title = "Shows",
        route = Screen.TvShowList.route,
        iconResource = R.drawable.ic_tv
    )
}