package com.pablodev.shamovie.media.domain

data class Movie(
    val title: String? = null,
    val company: String? = null,
    val releaseDate: String? = null,
    val genre: String? = null,
    val runtime: Int? = null,
    val ranking: Double? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val videoId: String? = null,
)
