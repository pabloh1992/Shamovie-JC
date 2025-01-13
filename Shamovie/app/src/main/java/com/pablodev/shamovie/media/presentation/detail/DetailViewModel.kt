package com.pablodev.shamovie.media.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablodev.shamovie.core.presentation.toUiText
import com.pablodev.shamovie.media.domain.MediaRepository
import com.pablodev.shamovie.media.presentation.list.MediaListAction
import com.pablodev.shamovie.media.presentation.list.MediaListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailViewModel(
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val TAG = "DetailViewModel"

    private var observeMovieListJob: Job? = null

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

            _state.update {
                it.copy(
                    media = media
                )
            }
        }
    }
}
