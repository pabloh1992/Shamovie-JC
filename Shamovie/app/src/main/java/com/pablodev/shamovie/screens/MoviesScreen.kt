package com.pablodev.shamovie.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pablodev.shamovie.R
import com.pablodev.shamovie.core.presentation.MediaCard
import com.pablodev.shamovie.core.presentation.MediaList
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.media.presentation.list.MediaListAction
import com.pablodev.shamovie.media.presentation.list.MediaListViewModel

@Composable
fun MoviesScreen (
    viewModel: MediaListViewModel,
    navController: NavHostController,
    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val mediaListScrollState = rememberLazyListState()



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 128.dp),
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