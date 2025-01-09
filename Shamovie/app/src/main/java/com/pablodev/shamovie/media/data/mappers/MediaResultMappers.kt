package com.pablodev.shamovie.media.data.mappers

import com.pablodev.shamovie.media.data.database.MovieEntity
import com.pablodev.shamovie.media.data.dto.SearchedResultDto
import com.pablodev.shamovie.media.domain.MediaResult

fun SearchedResultDto.toMediaResult(): MediaResult {
    return if (originalTitle != null || title != null) {
        MediaResult.Movie(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
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

fun MediaResult.toMovieEntity(): MovieEntity {
    if (this is MediaResult.Movie) {
        return MovieEntity(
            id = this.id,
            adult = this.adult,
            backdropPath = this.backdropPath,
            originalLanguage = this.originalLanguage,
            overview = this.overview,
            popularity = this.popularity,
            posterPath = this.posterPath,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            originalTitle = this.originalTitle,
            releaseDate = this.releaseDate,
            title = this.title,
            video = this.video
        )
    } else {
        throw IllegalArgumentException("Cannot convert TVShow to MovieEntity")
    }
}

fun MovieEntity.toMediaResult(): MediaResult.Movie {
    return MediaResult.Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        originalTitle = this.originalTitle,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video
    )
}
