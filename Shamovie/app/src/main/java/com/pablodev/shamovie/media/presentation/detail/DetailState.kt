package com.pablodev.shamovie.media.presentation.detail


import com.pablodev.shamovie.media.domain.MediaDetail

data class DetailState(
    val isLoading: Boolean = false,
    val media: MediaDetail? = null,
    val isTrailerPlaying: Boolean = false,
)