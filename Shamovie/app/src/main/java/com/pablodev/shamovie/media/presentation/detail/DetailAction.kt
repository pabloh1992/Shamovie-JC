package com.pablodev.shamovie.media.presentation.detail

import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.media.presentation.list.MediaListAction

sealed interface DetailAction {
    data object OnTrailerClick: DetailAction
}
