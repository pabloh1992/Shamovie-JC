package com.pablodev.shamovie.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pablodev.shamovie.core.presentation.ObserveAsEvents
import com.pablodev.shamovie.core.presentation.SnackbarController
import com.pablodev.shamovie.navigation.BottomNavItems
import com.pablodev.shamovie.navigation.MainNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen( ) {

    val TAG = "MainScreen"

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route

    Log.d(TAG, "Navigation - current route $currentRoute")

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    SetSystemBarsColor(
        color = if (currentRoute?.contains("Detail") == true)
            Color.Black else Color.Transparent
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            bottomBar = {
                if (currentRoute?.contains("Detail") == false && !currentRoute.contains("Search"))
                    BottomNavigationBar(navController)
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            },
        ) { paddingValues ->
            MainNavGraph(navController, paddingValues)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItems.Movies,
        BottomNavItems.Discover,
        BottomNavItems.TvShows
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(1)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary, // Bottom bar background color
        contentColor = MaterialTheme.colorScheme.onPrimary // Content color for icons and text
    ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = item.title)
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResource),
                            contentDescription = item.title,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
}

@Composable
fun SetSystemBarsColor(color: Color) {
    val systemUiController = rememberSystemUiController()

    // Set status bar and navigation bar to black
    systemUiController.setSystemBarsColor(
        color = color,
        darkIcons = false
    )

    systemUiController.setNavigationBarColor(
        color = Color.Black,
        darkIcons = false
    )

}
