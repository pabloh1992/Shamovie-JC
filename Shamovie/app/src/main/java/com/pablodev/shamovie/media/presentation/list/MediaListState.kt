package com.pablodev.shamovie.media.presentation.list

import com.pablodev.shamovie.media.domain.MediaResult

data class MediaListState(
    val mediaList: List<MediaResult> = emptyList(),
    val noMediaFound: Boolean? = false,
    val isDeleteModeActive: Boolean? = false
)
