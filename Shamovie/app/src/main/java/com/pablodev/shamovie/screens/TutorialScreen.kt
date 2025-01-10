package com.pablodev.shamovie.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pablodev.shamovie.navigation.Route

@Composable
fun TutorialScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "Tutorial"
            )
            Button(onClick = {
                navController.navigate(Route.Main) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }) {

            }
        }
    }
}

