package com.pablodev.shamovie.core.util

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.GsonBuilder
import com.pablodev.shamovie.media.domain.MediaDetail
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.media.presentation.detail.OriginRoute


fun MediaResult.toJson(): String {
    val gson = GsonBuilder().create()

    return when (this) {
        is MediaResult.Movie -> gson.toJson(this, MediaResult.Movie::class.java)
        is MediaResult.TVShow -> gson.toJson(this, MediaResult.TVShow::class.java)
    }
}

fun jsonToMediaResult(jsonMedia: String, isMovie: Boolean): MediaResult {
    val gson = GsonBuilder().create()

    return if (isMovie) {
        gson.fromJson(jsonMedia, MediaResult.Movie::class.java)
    } else {
        gson.fromJson(jsonMedia, MediaResult.TVShow::class.java)
    }
}

fun MediaDetail.toJson(): String {
    val gson = GsonBuilder().create()

    return when (this) {
        is MediaDetail.Movie -> gson.toJson(this, MediaDetail.Movie::class.java)
        is MediaDetail.TVShow -> gson.toJson(this, MediaDetail.TVShow::class.java)
    }
}

fun jsonToMedia(jsonMedia: String, isMovie: Boolean): MediaDetail {
    val gson = GsonBuilder().create()

    return if (isMovie) {
        gson.fromJson(jsonMedia, MediaDetail.Movie::class.java)
    } else {
        gson.fromJson(jsonMedia, MediaDetail.TVShow::class.java)
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

fun OriginRoute.toJson(): String {
    val gson = GsonBuilder().create()
    return gson.toJson(this, OriginRoute::class.java)
}

fun jsonToOriginRoute(json: String): OriginRoute {
    val gson = GsonBuilder().create()
    return gson.fromJson(json, OriginRoute::class.java)
}

fun ByteArray.toDecodedString(): String
    = Base64.encodeToString(this, Base64.DEFAULT)
