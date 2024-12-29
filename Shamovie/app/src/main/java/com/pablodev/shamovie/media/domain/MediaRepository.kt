package com.pablodev.shamovie.media.domain

import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result

interface MediaRepository {
    suspend fun searchMedia(
        media: String,
        query: String
    ): Result<List<MediaResult>, DataError.Remote>
}