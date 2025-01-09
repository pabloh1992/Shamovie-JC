package com.pablodev.shamovie.ui.theme

import androidx.compose.ui.graphics.Color

val Greenish = Color(0xFF16697a)
val Orangish = Color(0xFFFDA14F)
val Whity = Color(0xFFFFFFFF)
val BackgroundColor = Color(0xFFF5F1F4)
val TextGray = Color(0xFF212121)
val RatingInactive = Color(0xFFC9C9D1)
val OrangishLight = Orangish.copy(
    red = Orangish.red + (1f - Orangish.red) * 0.7f, // 70% closer to white
    green = Orangish.green + (1f - Orangish.green) * 0.7f,
    blue = Orangish.blue + (1f - Orangish.blue) * 0.7f
)