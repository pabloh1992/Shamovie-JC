package com.pablodev.shamovie.media.domain

import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.EmptyResult
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.core.util.MediaKey
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchMedia(
        mediaKey: String,
        query: String
    ): Result<List<MediaResult>, DataError.Remote>

    suspend fun getPosterImage(
        posterPath: String
    ) : Result<ByteArray, DataError.Remote>

    suspend fun getMediaDetail(
        mediaKey: String,
        id: String
    ): Result<MediaDetail, DataError.Remote>

//    suspend fun insertMediaResult(media: MediaResult): EmptyResult<DataError.Local>

    suspend fun insertMedia(media: MediaDetail): EmptyResult<DataError.Local>

    fun getMediaList(mediaKey: MediaKey): Flow<List<MediaResult>>

    suspend fun getMediaById(id: String, isMovie: Boolean): MediaDetail?

}