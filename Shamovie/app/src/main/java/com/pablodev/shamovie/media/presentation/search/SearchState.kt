package com.pablodev.shamovie.media.presentation.search

import com.pablodev.shamovie.core.presentation.UiText
import com.pablodev.shamovie.media.domain.MediaResult

data class SearchState (
    val searchQuery: String = "",
    val movieResults: List<MediaResult> = emptyList(),
    val tvShowsResults: List<MediaResult.TVShow> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)