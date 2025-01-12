package com.pablodev.shamovie.media.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsert(book: MovieEntity)

    @Query("SELECT * FROM MovieEntity ORDER BY insertedAt DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    suspend fun deleteMovie(id: String)

    @Query("SELECT * FROM MovieEntity WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: String): MovieEntity?

}