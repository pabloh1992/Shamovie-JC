package com.pablodev.shamovie.core.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.pablodev.shamovie.R
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.ui.theme.TextGray

@Composable
fun MediaItem(
    media: MediaResult,
    onMediaClick: (MediaResult) -> Unit,
    isSelected: Boolean,
    isSearchResult: Boolean = false,
    //selectedIconRes: Int,
   // modifier: Modifier = Modifier,
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

    media.posterDecoded?.let {
        val imageAsBytes: ByteArray =
            Base64.decode(it, Base64.DEFAULT)
        posterBitmap = BitmapFactory.decodeByteArray(
            imageAsBytes,
            0,
            imageAsBytes.size
        )
    }

    Box (
        modifier = Modifier
            .clickable { onMediaClick(media) }
    ){
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .height(40.dp)
            ) {
            }

            Box(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically // Align items to the center vertically
                ) {
                    // Column with the text and ratings on the right


                    Spacer(modifier = Modifier
                        .width(120.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp, bottom = 8.dp)
                    ) {
                        // Movie Name
                        Text(
                            text = name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Star Rating
                        StarRatingBar(
                            rating = rating
                        )

                        // Created By
                        Text(
                            text = createdBy,
                            fontSize = 15.sp,
                            color = TextGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Overview
                        Text(
                            text = overview,
                            fontSize = 13.sp,
                            color = TextGray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        }

        // Poster Image placed at the center of the two boxes
        Box(
            modifier = Modifier
                .size(120.dp, 162.dp)
                .align(Alignment.CenterStart)
                .padding(start = 16.dp, bottom = 16.dp)
        ) {

            if (!isSearchResult) {

                posterBitmap?.asImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 8.dp, x = 8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Image(
                        painter = painterResource(id = R.drawable.poster_not_available),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 8.dp, x = 8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

            } else {

                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }

                val painter = rememberAsyncImagePainter(
                    model = "https://image.tmdb.org/t/p/w500${media.posterPath}",
                    onSuccess = {
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Invalid image size"))
                            }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                when (val result = imageLoadResult) {
                    null -> PulseAnimation(
                        modifier = Modifier.size(60.dp)
                    )
                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(R.drawable.poster_not_available)
                            },
                            contentDescription = name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = 8.dp, x = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }
    }
}

fun Double.toFiveStarRating(): Float {
    return (this / 2).toFloat()
}
