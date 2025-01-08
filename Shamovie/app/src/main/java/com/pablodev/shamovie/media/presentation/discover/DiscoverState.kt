package com.pablodev.shamovie.media.presentation.discover

import com.pablodev.shamovie.core.presentation.UiText
import com.pablodev.shamovie.media.domain.MediaResult

data class DiscoverState(
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val mediaResult: MediaResult? = null,
    val errorMessage: UiText? = null
)