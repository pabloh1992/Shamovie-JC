package com.pablodev.shamovie.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pablodev.shamovie.core.presentation.MediaList
import com.pablodev.shamovie.core.util.toJson
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.media.presentation.list.MediaListAction
import com.pablodev.shamovie.media.presentation.list.MediaListViewModel
import com.pablodev.shamovie.navigation.Route

@Composable
fun MoviesScreen (
    viewModel: MediaListViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues
    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val mediaListScrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
        ) {

            if(state.mediaList.isNotEmpty()) {
                MediaList(
                    mediaList = state.mediaList,
                    onMediaClick = {
                        //viewModel.onAction(MediaListAction.OnMediaClick(it))

                        navController.navigate(
                            Route.Details(
                                query = null,
                                media = it.toJson(),
                                isMovie = it is MediaResult.Movie
                            )
                        )

                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    scrollState = mediaListScrollState,
                )
            }
        }
    }
}