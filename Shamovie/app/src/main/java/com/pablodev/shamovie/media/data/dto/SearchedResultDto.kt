package com.pablodev.shamovie.media.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedResultDto(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    val id: Int,
    @SerialName("original_language") val originalLanguage: String = "",
    @SerialName("original_title") val originalTitle: String? = null, // For movies
    @SerialName("original_name") val originalName: String? = null, // For TV shows
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String? = null, // For movies
    @SerialName("first_air_date") val firstAirDate: String? = null, // For TV shows
    val title: String? = null, // For movies
    val name: String? = null, // For TV shows
    val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("vote_count") val voteCount: Int = 0,
    @SerialName("origin_country") val originCountry: List<String>? = null,
)