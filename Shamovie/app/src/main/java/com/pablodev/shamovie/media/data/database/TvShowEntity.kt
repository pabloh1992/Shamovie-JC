package com.pablodev.shamovie.media.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvShowEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val originCountry: String,
    val originalName: String,
    val firstAirDate: String?,
    val name: String
)