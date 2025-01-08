package com.pablodev.shamovie.media.presentation.discover

sealed interface DiscoverAction {
    data object OnStart: DiscoverAction
    data class OnQueryFound(val query: String): DiscoverAction
    data object OnCancel: DiscoverAction
}