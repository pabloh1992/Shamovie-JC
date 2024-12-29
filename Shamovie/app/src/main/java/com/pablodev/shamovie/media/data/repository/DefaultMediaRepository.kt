package com.pablodev.shamovie.media.data.repository

import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.core.domain.map
import com.pablodev.shamovie.media.data.mappers.toMediaResult
import com.pablodev.shamovie.media.data.network.RemoteMediaDataSource
import com.pablodev.shamovie.media.domain.MediaRepository
import com.pablodev.shamovie.media.domain.MediaResult

class DefaultMediaRepository(
    private val remoteMediaDataSource: RemoteMediaDataSource
    // dao if needed
): MediaRepository {
    override suspend fun searchMedia(media: String, query: String): Result<List<MediaResult>, DataError.Remote> {
        return remoteMediaDataSource.searchMedia(media = media, query = query)
            .map { dto ->
                dto.results.map { it.toMediaResult() }
            }
    }
}