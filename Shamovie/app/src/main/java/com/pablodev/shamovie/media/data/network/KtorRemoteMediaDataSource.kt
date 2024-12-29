package com.pablodev.shamovie.media.data.network

import com.pablodev.shamovie.BuildConfig
import com.pablodev.shamovie.core.data.safeCall
import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.media.data.dto.SearchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.themoviedb.org/3"

class KtorRemoteMediaDataSource(
    private val httpClient: HttpClient
): RemoteMediaDataSource {

    override suspend fun searchMedia(
        media: String,
        query: String
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search/${media}"
            ) {
                parameter("query", query)
                parameter("api_key", BuildConfig.API_KEY)
            }
        }
    }
}