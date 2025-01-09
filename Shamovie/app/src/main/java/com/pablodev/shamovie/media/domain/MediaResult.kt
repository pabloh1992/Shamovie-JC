package com.pablodev.shamovie.media.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class MediaResult {
    abstract val id: Int
    abstract val adult: Boolean
    abstract val backdropPath: String?
    abstract val originalLanguage: String
    abstract val overview: String
    abstract val popularity: Double
    abstract val posterPath: String?
    abstract val voteAverage: Double
    abstract val voteCount: Int

    @Serializable
    data class Movie(
        override val id: Int,
        override val adult: Boolean,
        @SerialName("backdrop_path") override val backdropPath: String?,
        @SerialName("original_language") override val originalLanguage: String,
        override val overview: String,
        override val popularity: Double,
        @SerialName("poster_path") override val posterPath: String?,
        @SerialName("vote_average") override val voteAverage: Double,
        @SerialName("vote_count") override val voteCount: Int,
        @SerialName("original_title") val originalTitle: String,
        @SerialName("release_date") val releaseDate: String?,
        val title: String,
        val video: Boolean
    ) : MediaResult()

    @Serializable
    data class TVShow(
        override val id: Int,
        override val adult: Boolean,
        @SerialName("backdrop_path") override val backdropPath: String?,
        @SerialName("original_language") override val originalLanguage: String,
        override val overview: String,
        override val popularity: Double,
        @SerialName("poster_path") override val posterPath: String?,
        @SerialName("vote_average") override val voteAverage: Double,
        @SerialName("vote_count") override val voteCount: Int,
        @SerialName("origin_country") val originCountry: String,
        @SerialName("original_name") val originalName: String,
        @SerialName("first_air_date") val firstAirDate: String?,
        val name: String
    ) : MediaResult()
}
