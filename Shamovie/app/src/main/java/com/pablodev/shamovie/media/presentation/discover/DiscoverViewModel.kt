package com.pablodev.shamovie.media.presentation.discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
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
                    _state.update {
                        it.copy(
                            isListening = false,
                            isLoading = false,
                        )
                    }
                }
            }.onError {

            }
        }
    }
}