package com.pablodev.shamovie.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chaintech.videoplayer.ui.youtube.YouTubePlayerComposable
import com.pablodev.shamovie.R
import com.pablodev.shamovie.core.util.toImageBitmap
import com.pablodev.shamovie.media.domain.MediaDetail
import com.pablodev.shamovie.media.presentation.detail.DetailAction
import com.pablodev.shamovie.media.presentation.detail.DetailViewModel
import com.pablodev.shamovie.ui.theme.Gay
import com.pablodev.shamovie.ui.theme.Hellow

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    query: String? = null,
    id: String,
    isMovie: Boolean,
    //media: MediaDetail,
    paddingValues: PaddingValues
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.getMediaById(
        id = id,
        isMovie = isMovie
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter,
    ) {

        state.media?.let { media ->
            val name: String
            val createdBy: String
            val rating: Float
            val overview: String
            var posterBitmap: ImageBitmap? = null

            when (media) {
                is MediaDetail.Movie -> {
                    name = media.title
                    createdBy = media.releaseDate?.take(4) ?: ""
                    rating = media.voteAverage.toFloat()
                    overview = media.overview
                }

                is MediaDetail.TVShow -> {
                    name = media.name
                    createdBy = media.firstAirDate?.take(4) ?: ""
                    rating = media.voteAverage.toFloat()
                    overview = media.overview
                }
            }

            posterBitmap = media.posterDecoded?.toImageBitmap()

            Box {
                posterBitmap?.let {

                    // Poster image
                    if (!state.isTrailerPlaying) {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier
                                .height(600.dp)
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    // Gradient overlay
                    state.media?.videoTrailerId?.let {
                        if (!state.isTrailerPlaying) {
                            Column {
                                Spacer(modifier = Modifier.height(250.dp))
                                Box(
                                    modifier = Modifier
                                        .height(350.dp)
                                        .fillMaxWidth()
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent, // Transparent at the top
                                                    Color.Black.copy(alpha = 0.8f), // Almost black in the middle
                                                    Color.Black // Completely black at the end
                                                ),
                                                startY = 0f,
                                                endY = Float.POSITIVE_INFINITY // Ensure gradient covers the height
                                            )
                                        )
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()) // Make the column scrollable
                            .fillMaxSize()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(520.dp))



                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = name,
                                    maxLines = 2,
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                Text(
                                    modifier = Modifier.padding(top = 2.dp),
                                    text = "Company",
                                    style = TextStyle(
                                        color = Hellow,
                                        fontSize = 15.sp,
                                    )
                                )

                                Row(
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Text(
                                        text = createdBy,
                                        style = TextStyle(
                                            color = Gay,
                                            fontSize = 15.sp,
                                        )
                                    )

                                    Text(
                                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                                        text = "status",
                                        style = TextStyle(
                                            color = Gay,
                                            fontSize = 15.sp,
                                        ),
                                    )

                                    Text(
                                        text = "duration",
                                        style = TextStyle(
                                            color = Gay,
                                            fontSize = 15.sp,
                                        )
                                    )

                                }

                                Text(
                                    modifier = Modifier.padding(top = 2.dp),
                                    text = "⭐ $rating",
                                    style = TextStyle(
                                        color = Gay,
                                        fontSize = 15.sp,
                                    )
                                )
                            }

                            Image(
                                painter = painterResource(
                                    id = if(state.isTrailerPlaying) R.drawable.ic_btn_stop else R.drawable.ic_btn_play
                                ),
                                contentDescription = "Trailer button play",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(top = 12.dp, end = 5.dp)
                                    .clickable {
                                        viewModel.onAction(DetailAction.OnTrailerClick)
                                    }
                            )
                        }

                        Text(
                            text = overview,
                            style = TextStyle(
                                color = Gay,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Justify
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 24.dp)
                        )
                    }

                    // Youtube Player
                    state.media?.videoTrailerId?.let { videoId ->
                        if (state.isTrailerPlaying) {
                            YouTubePlayerComposable(
                                modifier = Modifier
                                    .height(520.dp),
                                videoId = videoId
                            )
                        }
                    }
                }
            }
        }
    }
}
