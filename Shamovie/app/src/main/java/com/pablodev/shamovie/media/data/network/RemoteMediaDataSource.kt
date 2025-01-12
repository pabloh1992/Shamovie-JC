package com.pablodev.shamovie.media.data.network

import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.media.data.dto.MediaDetailDto
import com.pablodev.shamovie.media.data.dto.SearchResponseDto

interface RemoteMediaDataSource {
    suspend fun searchMedia(
        mediaKey: String,
        query: String
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getPosterImage(
        posterPath: String
    ): Result<ByteArray, DataError.Remote>

    suspend fun getMediaDetail(
        mediaKey: String,
        id: String
    ): Result<MediaDetailDto, DataError.Remote>
}