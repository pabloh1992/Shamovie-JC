package com.pablodev.shamovie.media.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailDto(
    val adult: Boolean,
    val id: Int,
    @SerialName("original_language") val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerialName("poster_path") val posterPath: String?,
    val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("vote_count") val voteCount: Int = 0,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    val genres: List<GenreDto>? = listOf(),
    @SerialName("production_companies") val productionCompanies: List<ProductionCompanyDto>? = listOf(),

    @SerialName("original_title") val originalTitle: String? = null, // For movies
    val title: String? = null, // For movies
    @SerialName("release_date") val releaseDate: String? = null, // For movies
    val runtime: Int? = null, // For Movies

    @SerialName("original_name") val originalName: String? = null, // For TV shows
    val name: String? = null, // For TV shows
    @SerialName("first_air_date") val firstAirDate: String? = null, // For TV shows
    @SerialName("number_of_seasons") val numberOfSeasons: Int? = null, // For TV shows
    val status: String? = null, // For TV shows
)

@Serializable
data class ProductionCompanyDto(
    val id: Int?,
    val logo_path: String?,
    val name: String?,
    val origin_country: String?
)

@Serializable
data class GenreDto(
    val id: Int?,
    val name: String?
)
