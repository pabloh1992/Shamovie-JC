package com.pablodev.shamovie.core.util

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.GsonBuilder
import com.pablodev.shamovie.media.domain.MediaResult


fun MediaResult.toJson(): String {
    val gson = GsonBuilder().create()

    return when (this) {
        is MediaResult.Movie -> gson.toJson(this, MediaResult.Movie::class.java)
        is MediaResult.TVShow -> gson.toJson(this, MediaResult.TVShow::class.java)
    }
}

fun jsonToMedia(jsonMedia: String, isMovie: Boolean): MediaResult {
    val gson = GsonBuilder().create()

    return if (isMovie) {
        gson.fromJson(jsonMedia, MediaResult.Movie::class.java)
    } else {
        gson.fromJson(jsonMedia, MediaResult.TVShow::class.java)
    }
}

fun String.toImageBitmap(): ImageBitmap {

    val imageAsBytes: ByteArray =
        Base64.decode(this, Base64.DEFAULT)

    val posterBitmap = BitmapFactory.decodeByteArray(
        imageAsBytes,
        0,
        imageAsBytes.size
    )
    return posterBitmap.asImageBitmap()
}
