package com.pablodev.shamovie.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
import com.pablodev.shamovie.media.domain.MediaRepository
import org.koin.compose.koinInject

@Composable
fun DiscoverScreen() {

    val TAG = "DiscoverScreen"

    val repository: MediaRepository = koinInject()

    LaunchedEffect(Unit) {
        repository.searchMedia(
            media = "movie",
            query = "Dune"
        ).onSuccess {
            it.forEach { result ->
                Log.d(TAG, "Media result = $result")
            }
        }.onError {
            Log.d(TAG, "Error  = $it")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "Discover"
            )
        }
    }
}