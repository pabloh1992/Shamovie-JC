package com.pablodev.shamovie.media.presentation.search

import com.pablodev.shamovie.media.domain.MediaResult

sealed interface SearchAction {
    data class OnSearchQueryChange(val query: String): SearchAction
    data class OnMediaClick(val media: MediaResult): SearchAction
    data class OnTabSelected(val index: Int): SearchAction
}