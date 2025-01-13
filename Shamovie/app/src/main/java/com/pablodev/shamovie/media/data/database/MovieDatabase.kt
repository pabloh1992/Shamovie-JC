package com.pablodev.shamovie.media.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1
)

//@TypeConverters(
//    StringListTypeConverter::class
//)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao

    companion object {
        const val DB_MOVIE = "shamovie.db"
    }
}

