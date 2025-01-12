package com.pablodev.shamovie.media.data.network

import com.pablodev.shamovie.BuildConfig
import com.pablodev.shamovie.core.data.safeCall
import com.pablodev.shamovie.core.domain.DataError
import com.pablodev.shamovie.core.domain.Result
import com.pablodev.shamovie.media.data.dto.MediaDetailDto
import com.pablodev.shamovie.media.data.dto.SearchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.themoviedb.org/3"
private const val IMAGE_URL = "https://image.tmdb.org/t"

class KtorRemoteMediaDataSource(
    private val httpClient: HttpClient
): RemoteMediaDataSource {

    override suspend fun searchMedia(
        mediaKey: String,
        query: String
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search/${mediaKey}"
            ) {
                parameter("query", query)
                parameter("api_key", BuildConfig.API_KEY)
            }
        }
    }

    override suspend fun getPosterImage(
        posterPath: String
    ): Result<ByteArray, DataError.Remote> {
        return safeCall<ByteArray> {
            httpClient.get(
                urlString = "$IMAGE_URL/p/w500${posterPath}"
            ) {
                parameter("api_key", BuildConfig.API_KEY)
            }.body()
        }
    }

    override suspend fun getMediaDetail(
        mediaKey: String,
        id: String
    ): Result<MediaDetailDto, DataError.Remote> {
        return safeCall<MediaDetailDto> {
            httpClient.get(
                urlString = "$BASE_URL/${mediaKey}/${id}"
            ) {
                parameter("append_to_response", "videos")
                parameter("api_key", BuildConfig.API_KEY)
            }
        }
    }
}