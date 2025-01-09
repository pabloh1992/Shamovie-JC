package com.pablodev.shamovie.media.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablodev.shamovie.media.domain.MediaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaListViewModel(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val TAG = "MediaListViewModel"


    private var observeMovieListJob: Job? = null


    private val _state = MutableStateFlow(MediaListState())
    val state = _state
        .onStart {
            observeMovieList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: MediaListAction) {
        when (action) {
            is MediaListAction.OnMediaClick -> {

            }
            is MediaListAction.OnDeleteModeStart -> {

            }
            is MediaListAction.OnDeleteModeEnd -> {

            }
        }
    }

    fun getMovies() {
        viewModelScope.launch {
            mediaRepository.getMedia().collect { list ->
                _state.update {
                    it.copy(
                        mediaList = list
                    )
                }
            }
        }
    }


    private fun observeMovieList() {
        observeMovieListJob?.cancel()
        observeMovieListJob = mediaRepository
            .getMedia()
            .onEach { list ->

                list.forEach { movie ->
                    Log.d(TAG, "Saved movie = $movie")
                }

                _state.update {
                    it.copy(
                        mediaList = list
                    )
                }

            }
            .launchIn(viewModelScope)
    }
}