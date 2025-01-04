package com.pablodev.shamovie.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pablodev.shamovie.navigation.Screen

@Composable
fun MoviesScreen (
    navController: NavHostController,
    ) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "Movies"
            )
            Button(onClick = {
                navController.navigate(Screen.Details.route) {
                    popUpTo(navController.graph.startDestinationId) {

                    }
                }
            }) {

            }
        }
    }
}