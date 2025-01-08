package com.pablodev.shamovie.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.pablodev.shamovie.ui.theme.RatingInactive

@Composable
fun StarRatingBar(
    rating: Float = 5f,
    maxRating: Int = 5,
    starSize: Int = 18,
) {
    Row {
        for (i in 1..maxRating) {
            if (i <= rating.toInt()) {
                // Full stars
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(starSize.dp)
                )
            } else if (i == rating.toInt() + 1 && rating % 1 != 0f) {
                // Partial star
                PartialStar(fraction = rating % 1, starSize = starSize)
            } else {
                // Empty stars
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = RatingInactive,
                    modifier = Modifier
                        .size(starSize.dp)
                )
            }
        }
    }
}
@Composable
private fun PartialStar(fraction: Float, starSize: Int) {
    val customShape = FractionalClipShape(fraction)

    Box {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = RatingInactive,
            modifier = Modifier.size(starSize.dp)
        )
        Box(
            modifier = Modifier
                .graphicsLayer(
                    clip = true,
                    shape = customShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(starSize.dp)
            )
        }
    }
}


private class FractionalClipShape(private val fraction: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            rect = Rect(
                left = 0f,
                top = 0f,
                right = size.width * fraction,
                bottom = size.height
            )
        )
    }
}