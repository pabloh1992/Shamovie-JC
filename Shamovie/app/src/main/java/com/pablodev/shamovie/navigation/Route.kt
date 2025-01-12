package com.pablodev.shamovie.navigation

import com.pablodev.shamovie.media.domain.MediaResult
import kotlinx.serialization.Serializable

sealed interface Route {

    // Graph Routes
    @Serializable
    data object RootGraph: Route
    @Serializable
    data object MainGraph: Route

    // Screen Routes
    @Serializable
    data object Tutorial: Route
    @Serializable
    data object Main: Route
    @Serializable
    data object Discover: Route
    @Serializable
    data object MovieList: Route
    @Serializable
    data object TvShowList: Route
    @Serializable
    data class Details(
        val query: String? = null,
        val isMovie: Boolean,
        val media: String  // Encode and Decode
    ): Route
}
