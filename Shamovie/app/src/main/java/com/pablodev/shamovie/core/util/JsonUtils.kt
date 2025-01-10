package com.pablodev.shamovie.core.util

import com.google.gson.GsonBuilder
import com.pablodev.shamovie.media.domain.MediaResult


fun MediaResult.toJson(): String {
    val gson = GsonBuilder().create()

    return when (this) {
        is MediaResult.Movie -> gson.toJson(this, MediaResult.Movie::class.java)
        is MediaResult.TVShow -> gson.toJson(this, MediaResult.TVShow::class.java)
    }
}

fun jsonToMedia(jsonMedia: String, isMovie: Boolean): MediaResult {
    val gson = GsonBuilder().create()

    return if (isMovie) {
        gson.fromJson(jsonMedia, MediaResult.Movie::class.java)
    } else {
        gson.fromJson(jsonMedia, MediaResult.TVShow::class.java)
    }
}
