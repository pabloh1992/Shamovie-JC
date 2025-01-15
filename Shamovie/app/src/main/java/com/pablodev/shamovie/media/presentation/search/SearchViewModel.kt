package com.pablodev.shamovie.media.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
import com.pablodev.shamovie.core.presentation.toUiText
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.media.domain.MediaRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchViewModel (
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val TAG = "SearchViewModel"

    private var searchJobMovie: Job? = null
    private var searchJobTv: Job? = null


    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnMediaClick -> {

            }
            is SearchAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is SearchAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
            else -> {}
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                //searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJobMovie?.cancel()
                        searchJobMovie = searchMedia(query, MediaKey.MOVIE)
                        searchJobTv?.cancel()
                        searchJobTv = searchMedia(query, MediaKey.TV_SHOW)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchMedia(query: String, mediaKey: MediaKey) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        mediaRepository
            .searchMedia(
                mediaKey = mediaKey.value,
                query = query
            )
            .onSuccess { mediaResults ->

                if(mediaKey == MediaKey.MOVIE) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            movieResults = mediaResults
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            tvShowsResults = mediaResults
                        )
                    }
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        movieResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

}