package com.pablodev.shamovie.navigation

import com.pablodev.shamovie.R

sealed class BottomNavItems(
    val title : String,
    val route : Route,
    val iconResource: Int,
){
    data object Movies : BottomNavItems(
        title = "Movies",
        route = Route.MovieList,
        iconResource = R.drawable.ic_movie,
    )

    data object Discover : BottomNavItems(
        title = "Home",
        route = Route.Discover,
        iconResource = R.drawable.ic_mic
    )

    data object TvShows : BottomNavItems(
        title = "Shows",
        route = Route.TvShowList,
        iconResource = R.drawable.ic_tv
    )
}