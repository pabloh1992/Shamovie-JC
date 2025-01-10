package com.pablodev.shamovie.media.data.repository

import android.database.sqlite.SQLiteException
import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.EmptyResult
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.core.domain.map
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.media.data.database.MovieDao
import com.pablodev.shamovie.media.data.database.TvShowDao
import com.pablodev.shamovie.media.data.mappers.toMediaResult
import com.pablodev.shamovie.media.data.mappers.toMovieEntity
import com.pablodev.shamovie.media.data.mappers.toTvShowEntity
import com.pablodev.shamovie.media.data.network.RemoteMediaDataSource
import com.pablodev.shamovie.media.domain.MediaRepository
import com.pablodev.shamovie.media.domain.MediaResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultMediaRepository(
    private val remoteMediaDataSource: RemoteMediaDataSource,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
): MediaRepository {

    override suspend fun searchMedia(
        media: String,
        query: String
    ): Result<List<MediaResult>, DataError.Remote> {
        return remoteMediaDataSource.searchMedia(media = media, query = query)
            .map { dto ->
                dto.results.map { it.toMediaResult() }
            }
    }

    override suspend fun getPosterImage(posterPath: String): Result<ByteArray, DataError.Remote> {
        return remoteMediaDataSource.getPosterImage(posterPath)
    }


    override suspend fun insertMedia(media: MediaResult): Result<Unit, DataError.Local> {
        return try {
            when (media) {
                is MediaResult.Movie -> {
                    movieDao.upsert(media.toMovieEntity())
                }
                is MediaResult.TVShow -> {
                    tvShowDao.upsert(media.toTvShowEntity())
                }
            }
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getMedia(mediaKey: MediaKey): Flow<List<MediaResult>> {

        when (mediaKey) {
            MediaKey.MOVIE -> return movieDao.getMovies().map {
                it.map { entity ->
                    entity.toMediaResult()
                }
            }
            MediaKey.TV_SHOW -> return tvShowDao.getTvShows().map {
                it.map { entity ->
                    entity.toMediaResult()
                }
            }
        }
    }
}
