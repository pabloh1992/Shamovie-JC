package com.pablodev.shamovie.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pablodev.shamovie.core.presentation.toFiveStarRating
import com.pablodev.shamovie.media.domain.MediaResult

@Composable
fun DetailScreen(
    query: String? = null,
    media: MediaResult
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        val name: String
        val createdBy: String
        val rating: Float
        val overview: String
        var posterBitmap: Bitmap? = null

        when(media) {
            is MediaResult.Movie -> {
                name = media.title
                createdBy = media.releaseDate?.take(4) ?: ""
                rating = media.voteAverage.toFiveStarRating()
                overview = media.overview
            }
            is MediaResult.TVShow -> {
                name = media.name
                createdBy = media.firstAirDate?.take(4) ?: ""
                rating = media.voteAverage.toFiveStarRating()
                overview = media.overview
            }
        }

        Column {
            Text(
                text = query ?: ""
            )
            Text(
                text = name
            )
            Text(
                text = createdBy
            )
            Text(
                text = overview
            )
        }
    }
}