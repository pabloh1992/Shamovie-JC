package com.pablodev.shamovie.media.presentation.list

import com.pablodev.shamovie.media.domain.MediaResult


sealed interface MediaListAction {
    data class OnMediaClick(val media: MediaResult): MediaListAction
    data object OnDeleteModeStart: MediaListAction
    data class OnDeleteModeEnd(val mediaToDelete: List<MediaResult>): MediaListAction
}