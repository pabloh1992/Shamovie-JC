package com.pablodev.shamovie.media.domain

import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.EmptyResult
import com.pablodev.shamovie.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchMedia(
        media: String,
        query: String
    ): Result<List<MediaResult>, DataError.Remote>

    suspend fun insertMedia(media: MediaResult): EmptyResult<DataError.Local>

    fun getMedia(): Flow<List<MediaResult>>


}