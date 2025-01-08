package com.pablodev.shamovie.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pablodev.shamovie.R
import com.pablodev.shamovie.core.presentation.MediaCard

@Composable
fun MoviesScreen (
    navController: NavHostController,
    ) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            MediaCard(
                posterImageRes = R.drawable.poster_not_available,
                isSelected = false,
                selectedIconRes = R.drawable.poster_not_available,
                name = "Inuyasha",
                rating = 3.5f,
                createdBy = "ANIMAX",
                overview = "Kagome Hirugashu  is a modern day young gril who lives with her family by the old Higure shrine. Unbeknownst to Kagome, she is the reincarnation of priestess Kikyo",
            )
        }
    }
}