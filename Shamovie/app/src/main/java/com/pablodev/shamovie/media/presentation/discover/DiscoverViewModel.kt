package com.pablodev.shamovie.media.presentation.discover

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
import com.pablodev.shamovie.core.presentation.UiText
import com.pablodev.shamovie.core.presentation.toUiText
import com.pablodev.shamovie.media.domain.MediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DiscoverState())
    val state = _state

    private val TAG = "DiscoverViewModel"

    fun onAction(action: DiscoverAction) {
        when (action) {
            is DiscoverAction.OnStart -> {
                _state.update {
                    it.copy(
                        isListening = true,
                        isLoading = false,
                    )
                }
            }
            is DiscoverAction.OnQueryFound -> {
                getMedia(action.query)
                _state.update {
                    it.copy(
                        isListening = false,
                        isLoading = true,
                        query = action.query
                    )
                }
            }

            is DiscoverAction.OnMediaOptionChanged -> {
                _state.update {
                    it.copy(
                        mediaOption = action.mediaOption
                    )
                }
            }

            is DiscoverAction.OnCancel -> {
                _state.update {
                    it.copy(
                        isListening = false,
                        isLoading = false,
                    )
                }
            }
            is DiscoverAction.ResetResult -> {
                _state.update {
                    it.copy(
                        mediaResult = null,
                        errorMessage = null,
                        query = null,
                        noResults = false
                    )
                }
            }
        }
    }

    private fun getMedia(query: String) {
        viewModelScope.launch {
            mediaRepository.searchMedia(
                media = state.value.mediaOption.value,
                query = query
            ).onSuccess { list ->

                if (list.isNotEmpty()) {
                    val media = list.first()
                    Log.d(TAG, "Media discovered result = $media")
                    media.posterPath?.let { posterPath ->
                        mediaRepository.getPosterImage(posterPath = posterPath)
                            .onSuccess { posterByte ->
                                media.posterDecoded = Base64.encodeToString(posterByte, Base64.DEFAULT)
                                mediaRepository.insertMedia(media)
                                _state.update {
                                    it.copy(
                                        isListening = false,
                                        isLoading = false,
                                        mediaResult = media
                                    )
                                }
                            }
                            .onError {
                                media.posterDecoded = null
                                mediaRepository.insertMedia(media)
                                _state.update {
                                    it.copy(
                                        isListening = false,
                                        isLoading = false,
                                        mediaResult = media
                                    )
                                }
                            }
                    }
                } else {
                    _state.update {
                        it.copy(
                            isListening = false,
                            isLoading = false,
                            errorMessage = UiText.DynamicString("No results found for $query"),
                            noResults = true
                        )
                    }
                }

            }.onError { error ->
                _state.update {
                    it.copy(
                        isListening = false,
                        isLoading = false,
                        errorMessage = error.toUiText(),
                    )
                }
            }
        }
    }
}
