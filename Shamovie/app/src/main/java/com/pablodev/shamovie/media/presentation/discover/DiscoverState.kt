package com.pablodev.shamovie.media.presentation.discover

import com.pablodev.shamovie.core.presentation.UiText
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.media.domain.MediaDetail

data class DiscoverState(
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val media: MediaDetail? = null,
    val errorMessage: UiText? = null,
    val query: String? = null,
    val mediaOption: MediaKey = MediaKey.MOVIE,
    val noResults: Boolean? = false
)