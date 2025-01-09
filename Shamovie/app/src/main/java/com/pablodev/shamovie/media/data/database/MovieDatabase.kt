package com.pablodev.shamovie.media.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [MovieEntity::class],
    version = 1
)

//@TypeConverters(
//    StringListTypeConverter::class
//)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val DB_MOVIE = "movie.db"
    }
}

