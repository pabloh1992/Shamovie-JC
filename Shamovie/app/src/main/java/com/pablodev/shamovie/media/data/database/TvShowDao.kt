package com.pablodev.shamovie.media.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Upsert
    suspend fun upsert(tvShow: TvShowEntity)

    @Query("SELECT * FROM TvShowEntity ORDER BY insertedAt DESC")
    fun getTvShows(): Flow<List<TvShowEntity>>

    @Query("DELETE FROM TvShowEntity WHERE id = :id")
    suspend fun deleteTvShow(id: String)
}