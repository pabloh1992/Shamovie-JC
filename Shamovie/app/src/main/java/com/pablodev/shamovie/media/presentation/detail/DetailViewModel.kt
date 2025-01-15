package com.pablodev.shamovie.media.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
import com.pablodev.shamovie.core.util.toDecodedString
import com.pablodev.shamovie.media.domain.MediaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailViewModel(
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val TAG = "DetailViewModel"

    private val _state = MutableStateFlow(DetailState())
    val state = _state

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.OnTrailerClick -> {
                _state.update {
                    it.copy(
                        isTrailerPlaying = !state.value.isTrailerPlaying
                    )
                }
            }
        }
    }

    fun getMediaById(id:String, isMovie: Boolean) {
        viewModelScope.launch {
            val media = mediaRepository.getMediaById(
                id = id,
                isMovie = isMovie
            )

            media?.let { m ->
                _state.update {
                    it.copy(
                        media = m
                    )
                }
            } ?: run {

                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                mediaRepository.getMediaDetail(
                    mediaKey = if (isMovie) "movie" else "tv",
                    id = id
                ).onSuccess { md ->

                    md.posterPath?.let { posterPath ->
                        mediaRepository.getPosterImage(
                            posterPath = posterPath
                        ).onSuccess { posterBytes ->
                            md.posterDecoded = posterBytes.toDecodedString()

                            state.update {
                                it.copy(
                                    isLoading = false,
                                    media = md
                                )
                            }


                        }.onError {

                            state.update {
                                it.copy(
                                    isLoading = false,
                                    media = md
                                )
                            }
                        }
                    } ?: run {

                        state.update {
                            it.copy(
                                isLoading = false,
                                media = null
                            )
                        }

                    }
                }.onError {
                    state.update {
                        it.copy(
                            isLoading = false,
                            media = null
                        )
                    }
                }
            }
        }
    }
}
