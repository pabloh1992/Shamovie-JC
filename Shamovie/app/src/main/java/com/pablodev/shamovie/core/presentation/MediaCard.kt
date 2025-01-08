package com.pablodev.shamovie.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablodev.shamovie.ui.theme.Greenish
import com.pablodev.shamovie.ui.theme.Orangish
import com.pablodev.shamovie.ui.theme.TextGray
import com.pablodev.shamovie.ui.theme.Whity

@Composable
fun MediaCard(
    posterImageRes: Int,
    isSelected: Boolean,
    selectedIconRes: Int,
    name: String,
    rating: Float,
    createdBy: String,
    overview: String,
    modifier: Modifier = Modifier,
) {


    Box {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Ensure it spans the width
                    .background(Color.Transparent)
                    .height(60.dp)
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
                            fontSize = 18.sp,
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
                .padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(id = posterImageRes),
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
