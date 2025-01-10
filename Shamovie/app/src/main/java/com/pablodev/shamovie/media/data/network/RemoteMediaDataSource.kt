package com.pablodev.shamovie.media.data.network

import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.media.data.dto.SearchResponseDto
import com.pablodev.shamovie.media.data.dto.SearchedResultDto

interface RemoteMediaDataSource {
    suspend fun searchMedia(
        media: String,
        query: String
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getPosterImage(
        posterPath: String
    ) : Result<ByteArray, DataError.Remote>
}