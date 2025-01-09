package com.pablodev.shamovie.di

import androidx.room.Room
import com.pablodev.shamovie.core.data.HttpClientFactory
import com.pablodev.shamovie.media.data.database.MovieDatabase
import com.pablodev.shamovie.media.data.network.KtorRemoteMediaDataSource
import com.pablodev.shamovie.media.data.network.RemoteMediaDataSource
import com.pablodev.shamovie.media.data.repository.DefaultMediaRepository
import com.pablodev.shamovie.media.domain.MediaRepository
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val modules = module {
    // Provide the CIO engine
    single { CIO }

    // Provide the HttpClient using the CIO engine
    single { HttpClientFactory.create(get()) }

    // Provide RemoteMediaDataSource
    singleOf(::KtorRemoteMediaDataSource).bind<RemoteMediaDataSource>()

    // Provide MediaRepository
    singleOf(::DefaultMediaRepository).bind<MediaRepository>()

    viewModelOf(::DiscoverViewModel)

    single {
        Room.databaseBuilder(get(), MovieDatabase::class.java, MovieDatabase.DB_MOVIE)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<MovieDatabase>().movieDao }

}