package com.pablodev.shamovie.media.data.repository

import android.database.sqlite.SQLiteException
import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.core.domain.map
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.media.data.database.MovieDao
import com.pablodev.shamovie.media.data.database.TvShowDao
import com.pablodev.shamovie.media.data.mappers.toMediaDetail
import com.pablodev.shamovie.media.data.mappers.toMediaResult
import com.pablodev.shamovie.media.data.mappers.toMovieEntity
import com.pablodev.shamovie.media.data.mappers.toTvShowEntity
import com.pablodev.shamovie.media.data.network.RemoteMediaDataSource
import com.pablodev.shamovie.media.domain.MediaDetail
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
        mediaKey: String,
        query: String
    ): Result<List<MediaResult>, DataError.Remote> {
        return remoteMediaDataSource.searchMedia(mediaKey = mediaKey, query = query)
            .map { dto ->
                dto.results.map { it.toMediaResult() }
            }
    }

    override suspend fun getPosterImage(posterPath: String): Result<ByteArray, DataError.Remote> {
        return remoteMediaDataSource.getPosterImage(posterPath)
    }

    override suspend fun getMediaDetail(
        mediaKey: String,
        id: String
    ): Result<MediaDetail, DataError.Remote> {
        return remoteMediaDataSource.getMediaDetail(mediaKey, id)
            .map { dto ->
                dto.toMediaDetail()
            }
    }


//    override suspend fun insertMediaResult(media: MediaResult): Result<Unit, DataError.Local> {
//        return try {
//            when (media) {
//                is MediaResult.Movie -> {
//                    movieDao.upsert(media.toMovieEntity())
//                }
//                is MediaResult.TVShow -> {
//                    tvShowDao.upsert(media.toTvShowEntity())
//                }
//            }
//            Result.Success(Unit)
//        } catch (e: SQLiteException) {
//            Result.Error(DataError.Local.DISK_FULL)
//        }
//    }

    override suspend fun insertMedia(media: MediaDetail): Result<Unit, DataError.Local> {
        return try {
            when (media) {
                is MediaDetail.Movie -> {
                    movieDao.upsert(media.toMovieEntity())
                }
                is MediaDetail.TVShow -> {
                    tvShowDao.upsert(media.toTvShowEntity())
                }
            }
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getMediaList(mediaKey: MediaKey): Flow<List<MediaResult>> {

        return when (mediaKey) {
            MediaKey.MOVIE -> movieDao.getMovies().map {
                it.map { entity ->
                    entity.toMediaResult()
                }
            }

            MediaKey.TV_SHOW -> tvShowDao.getTvShows().map {
                it.map { entity ->
                    entity.toMediaResult()
                }
            }
        }
    }

    override suspend fun getMediaById(id: String, isMovie: Boolean): MediaDetail? {
        return if (isMovie) {
            movieDao.getMovieById(id = id)?.toMediaDetail()
        } else {
            tvShowDao.getTvShowById(id = id)?.toMediaDetail()
        }
    }
}
