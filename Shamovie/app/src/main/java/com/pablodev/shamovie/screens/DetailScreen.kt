package com.pablodev.shamovie.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavController
import chaintech.videoplayer.ui.youtube.YouTubePlayerComposable
import com.pablodev.shamovie.R
import com.pablodev.shamovie.core.util.toImageBitmap
import com.pablodev.shamovie.media.domain.MediaDetail
import com.pablodev.shamovie.media.presentation.detail.DetailAction
import com.pablodev.shamovie.media.presentation.detail.DetailViewModel
import com.pablodev.shamovie.ui.theme.Gay
import com.pablodev.shamovie.ui.theme.Hellow
import com.pablodev.shamovie.ui.theme.ShowCanceled
import com.pablodev.shamovie.ui.theme.ShowEnded

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController,
    query: String? = null,
    id: String,
    isMovie: Boolean,
    paddingValues: PaddingValues
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.getMediaById(
        id = id,
        isMovie = isMovie
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = Color.Transparent,
                            navigationIconContentColor = Color.White
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.TopCenter,
        ) {

            state.media?.let { media ->
                val name: String
                val rating: Float
                val overview: String
                var posterBitmap: ImageBitmap? = null

                when (media) {
                    is MediaDetail.Movie -> {
                        name = media.title
                        rating = media.voteAverage.toFloat()
                        overview = media.overview
                    }

                    is MediaDetail.TVShow -> {
                        name = media.name
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


                                    when (media) {
                                        is MediaDetail.Movie -> MovieDetail(media)
                                        is MediaDetail.TVShow -> TvShowDetail(media)
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
                                        id = if (state.isTrailerPlaying) R.drawable.ic_btn_stop else R.drawable.ic_btn_play
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
}

@Composable
fun MovieDetail(
    movie: MediaDetail.Movie
) {

    Column {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = movie.productionCompany ?: "",
            style = TextStyle(
                color = Hellow,
                fontSize = 15.sp,
            )
        )
    }

    Row(
        modifier = Modifier.padding(top = 2.dp)
    ) {
        Text(
            text = movie.releaseDate?.take(4) ?: "",
            style = TextStyle(
                color = Gay,
                fontSize = 15.sp,
            )
        )

        Text(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            text = movie.genre ?: "",
            style = TextStyle(
                color = Gay,
                fontSize = 15.sp,
            ),
        )

        Text(
            text = movie.runtime?.toMinutes() ?: "",
            style = TextStyle(
                color = Gay,
                fontSize = 15.sp,
            )
        )

    }
}

@Composable
fun TvShowDetail(
    tvShow: MediaDetail.TVShow
) {

    Column {
        Row(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                text = tvShow.productionCompany ?: "",
                style = TextStyle(
                    color = Hellow,
                    fontSize = 15.sp,
                )
            )

            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = tvShow.firstAirDate?.take(4) ?: "",
                style = TextStyle(
                    color = Gay,
                    fontSize = 15.sp,
                ),
            )

            // ['Returning Series', 'Planned', 'In Production', 'Ended', 'Canceled', 'Pilot']
            Text(
                text = tvShow.status ?: "",
                style = TextStyle(
                    color = when (tvShow.status) {
                        "Ended" -> ShowEnded
                        "Canceled" -> ShowCanceled
                        else -> Hellow
                    },
                    fontSize = 15.sp,
                )
            )

        }
    }

    Text(
        modifier = Modifier.padding(top = 2.dp),
        text = tvShow.numberOfSeasons?.let {"Seasons: $it"} ?: "",
        style = TextStyle(
            color = Gay,
            fontSize = 15.sp,
        )
    )
}

fun Int.toMinutes(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return "${hours}h ${remainingMinutes}m"
}
