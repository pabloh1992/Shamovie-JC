package com.pablodev.shamovie.media.data.mappers

import com.pablodev.shamovie.media.data.dto.SearchedResultDto
import com.pablodev.shamovie.media.domain.MediaResult

fun SearchedResultDto.toMediaResult(): MediaResult {
    return if (originalTitle != null || title != null) {
        MediaResult.Movie(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            originalTitle = originalTitle ?: "",
            releaseDate = releaseDate,
            title = title ?: "",
            video = video ?: false
        )
    } else {
        MediaResult.TVShow(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            originCountry = emptyList(), // Assuming a default empty list if not provided
            originalName = originalName ?: "", // Assuming `originalName` is always non-null for TV shows
            firstAirDate = firstAirDate,
            name = name ?: "" // Assuming `name` is always non-null for TV shows
        )
    }
}