package com.pablodev.shamovie.media.data.mappers

import com.pablodev.shamovie.media.data.database.MovieEntity
import com.pablodev.shamovie.media.data.database.TvShowEntity
import com.pablodev.shamovie.media.data.dto.MediaDetailDto
import com.pablodev.shamovie.media.domain.MediaDetail
import com.pablodev.shamovie.media.domain.MediaResult

fun MediaDetailDto.toMediaDetail(): MediaDetail {
    return if (originalTitle != null || title != null) {
        MediaDetail.Movie(
            id = id,
            adult = adult,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            genre = genres?.firstOrNull()?.name.orEmpty(),
            productionCompany = productionCompanies?.firstOrNull()?.name.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            title = title.orEmpty(),
            releaseDate = releaseDate,
            runtime = runtime,
            video = video ?: false,
            posterDecoded = null
        )
    } else {
        MediaDetail.TVShow(
            id = id,
            adult = adult,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            originCountry = originCountry,
            genre = genres?.firstOrNull()?.name.orEmpty(),
            productionCompany = productionCompanies?.firstOrNull()?.name.orEmpty(),
            originalName = originalName.orEmpty(),
            name = name.orEmpty(),
            firstAirDate = firstAirDate,
            numberOfSeasons = numberOfSeasons,
            status = status,
            posterDecoded = null
        )
    }
}

fun MediaDetail.toMovieEntity(): MovieEntity {
    if (this is MediaDetail.Movie) {
        return MovieEntity(
            id = this.id,
            adult = this.adult,
            originalLanguage = this.originalLanguage,
            overview = this.overview,
            popularity = this.popularity,
            posterPath = this.posterPath,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            video = this.video,
            genre = this.genre,
            productionCompany = this.productionCompany,

            title = this.title,
            originalTitle = this.originalTitle,
            releaseDate = this.releaseDate,
            runtime = this.runtime,

            posterDecoded = posterDecoded
        )
    } else {
        throw IllegalArgumentException("Cannot convert TVShow to MovieEntity")
    }
}

fun MediaDetail.toTvShowEntity(): TvShowEntity {
    if (this is MediaDetail.TVShow) {
        return TvShowEntity(
            id = this.id,
            adult = this.adult,
            originalLanguage = this.originalLanguage,
            overview = this.overview,
            popularity = this.popularity,
            posterPath = this.posterPath,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            genre = this.genre,
            productionCompany = this.productionCompany,

            originalName = this.originalName,
            firstAirDate = this.firstAirDate,
            name = this.name,
            numberOfSeasons = this.numberOfSeasons,
            status = this.status,

            posterDecoded = posterDecoded
        )
    } else {
        throw IllegalArgumentException("Cannot convert Movie to TvShowEntity")
    }
}

fun MovieEntity.toMediaDetail(): MediaDetail.Movie {
    return MediaDetail.Movie(
        id = this.id,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genre = this.genre,
        productionCompany = productionCompany,

        originalTitle = this.originalTitle,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        runtime = this.runtime,

        posterDecoded = this.posterDecoded
    )
}

fun TvShowEntity.toMediaDetail(): MediaDetail.TVShow {
    return MediaDetail.TVShow(
        id = this.id,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genre = this.genre,
        productionCompany = this.productionCompany,

        originalName = this.originalName,
        firstAirDate = this.firstAirDate,
        name = this.name,
        numberOfSeasons = this.numberOfSeasons,
        status = this.status,

        posterDecoded = this.posterDecoded
    )
}