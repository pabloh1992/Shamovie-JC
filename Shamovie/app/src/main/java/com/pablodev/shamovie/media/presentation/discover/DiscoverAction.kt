package com.pablodev.shamovie.media.presentation.discover

import com.pablodev.shamovie.core.util.MediaKey

sealed interface DiscoverAction {
    data object OnStart: DiscoverAction
    data class OnQueryFound(val query: String): DiscoverAction
    data class OnMediaOptionChanged(val mediaOption: MediaKey): DiscoverAction
    data object OnCancel: DiscoverAction
    data object ResetResult: DiscoverAction
}