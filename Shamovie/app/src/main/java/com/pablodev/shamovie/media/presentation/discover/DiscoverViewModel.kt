package com.pablodev.shamovie.media.presentation.discover

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
                media = "movie",
                query = query
            ).onSuccess { list ->

                list.forEach { result ->
                    Log.d(TAG, "Media result = $result")
                }

                if (list.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            isListening = false,
                            isLoading = false,
                            mediaResult = list.first()
                        )
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