package com.pablodev.shamovie.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pablodev.shamovie.core.presentation.MediaList
import com.pablodev.shamovie.media.presentation.list.MediaListAction
import com.pablodev.shamovie.media.presentation.list.MediaListViewModel

@Composable
fun TvShowsScreen(
    viewModel: MediaListViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val mediaListScrollState = rememberLazyListState()

    LaunchedEffect(state.mediaList) {
        if (state.mediaList.isNotEmpty()) {
            mediaListScrollState.scrollToItem(0)
        }
    }

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
                        viewModel.onAction(MediaListAction.OnMediaClick(it))
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