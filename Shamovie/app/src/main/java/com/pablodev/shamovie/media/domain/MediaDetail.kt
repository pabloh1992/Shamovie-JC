package com.pablodev.shamovie.media.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class MediaDetail {
    abstract val id: Int
    abstract val adult: Boolean
    abstract val originalLanguage: String
    abstract val overview: String
    abstract val popularity: Double
    abstract val posterPath: String?
    abstract val voteAverage: Double
    abstract val voteCount: Int
    abstract val genre: String?
    abstract val productionCompany: String?
    abstract var posterDecoded: String?

    @Serializable
    data class Movie(
        override val id: Int,
        override val adult: Boolean,
        @SerialName("original_language") override val originalLanguage: String,
        override val overview: String,
        override val popularity: Double,
        @SerialName("poster_path") override val posterPath: String?,
        @SerialName("vote_average") override val voteAverage: Double,
        @SerialName("vote_count") override val voteCount: Int,
        val video: Boolean = false,

        override val genre: String? = null,
        override val productionCompany: String? = null,

        @SerialName("original_title") val originalTitle: String,
        val title: String,
        @SerialName("release_date") val releaseDate: String?,
        val runtime: Int? = null,

        override var posterDecoded: String? = null
    ) : MediaDetail()

    @Serializable
    data class TVShow(
        override val id: Int,
        override val adult: Boolean,
        @SerialName("original_language") override val originalLanguage: String,
        override val overview: String,
        override val popularity: Double,
        @SerialName("poster_path") override val posterPath: String?,
        @SerialName("vote_average") override val voteAverage: Double,
        @SerialName("vote_count") override val voteCount: Int,
        @SerialName("origin_country") val originCountry: List<String>? = listOf(),
        override val genre: String? = null,
        override val productionCompany: String? = null,

        @SerialName("original_name") val originalName: String,
        val name: String,
        @SerialName("first_air_date") val firstAirDate: String?,
        @SerialName("number_of_seasons") val numberOfSeasons: Int? = null,
        val status: String? = null,

        override var posterDecoded: String? = null
    ) : MediaDetail()
}