package com.pablodev.shamovie.media.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    val results: List<SearchedResultDto>
)