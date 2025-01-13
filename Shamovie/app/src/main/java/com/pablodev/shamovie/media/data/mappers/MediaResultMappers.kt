package com.pablodev.shamovie.media.data.mappers

import com.pablodev.shamovie.media.data.database.MovieEntity
import com.pablodev.shamovie.media.data.database.TvShowEntity
import com.pablodev.shamovie.media.data.dto.SearchedResultDto
import com.pablodev.shamovie.media.domain.MediaResult

fun SearchedResultDto.toMediaResult(): MediaResult {
    return if (originalTitle != null || title != null) {
        MediaResult.Movie(
            id = id,
            adult = adult,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            originalTitle = originalTitle ?: "",
            releaseDate = releaseDate,
            title = title ?: ""
        )
    } else {
        MediaResult.TVShow(
            id = id,
            adult = adult,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            originalName = originalName ?: "", // Assuming `originalName` is always non-null for TV shows
            firstAirDate = firstAirDate,
            name = name ?: "" // Assuming `name` is always non-null for TV shows
        )
    }
}

fun MovieEntity.toMediaResult(): MediaResult.Movie {
    return MediaResult.Movie(
        id = this.id,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        originalTitle = this.originalTitle,
        releaseDate = this.releaseDate,
        title = this.title,
        posterDecoded = this.posterDecoded
    )
}

fun TvShowEntity.toMediaResult(): MediaResult.TVShow {
    return MediaResult.TVShow(
        id = this.id,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        originalName = this.originalName,
        firstAirDate = this.firstAirDate,
        name = this.name,
        posterDecoded = this.posterDecoded
    )
}

