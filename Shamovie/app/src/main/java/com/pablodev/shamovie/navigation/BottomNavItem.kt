package com.pablodev.shamovie.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItems(
    val title : String,
    val route : String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
){
    data object Movies : BottomNavItems(
        title = "Movies",
        route = Screen.MovieList.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    data object Discover : BottomNavItems(
        title = "Discover",
        route = Screen.Discover.route,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
    )

    data object TvShows : BottomNavItems(
        title = "Tv",
        route = Screen.TvShowList.route,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    )
}