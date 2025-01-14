package com.pablodev.shamovie.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablodev.shamovie.media.domain.MediaResult


@Composable
fun MediaList(
    mediaList: List<MediaResult>,
    onMediaClick: (MediaResult) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    isSearchResult: Boolean = false
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = mediaList,
            key = { it.id }
        ) { media ->
            MediaItem(
                media = media,
                onMediaClick = {
                    onMediaClick(media)
                },
                isSelected = false,
                isSearchResult = isSearchResult
            )
        }
    }
}