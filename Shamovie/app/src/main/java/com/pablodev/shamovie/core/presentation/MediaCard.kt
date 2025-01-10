package com.pablodev.shamovie.core.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablodev.shamovie.R
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.ui.theme.TextGray

@Composable
fun MediaCard(
    media: MediaResult,
    onMediaClick: (MediaResult) -> Unit,
    isSelected: Boolean,
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

    Box {
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
        }
    }
}

fun Double.toFiveStarRating(): Float {
    return (this / 2).toFloat()
}
