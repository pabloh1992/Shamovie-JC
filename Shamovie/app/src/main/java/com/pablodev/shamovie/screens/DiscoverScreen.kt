package com.pablodev.shamovie.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
import com.pablodev.shamovie.media.domain.MediaRepository
import com.pablodev.shamovie.media.presentation.discover.DiscoverAction
import com.pablodev.shamovie.media.presentation.discover.DiscoverState
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.speech.SpeechRecognizer
import org.koin.compose.koinInject

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    navController: NavHostController,
) {

    val TAG = "DiscoverScreen"

    val repository: MediaRepository = koinInject()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val speechRecognizer = SpeechRecognizer(LocalContext.current)

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
            Button(onClick = {
                viewModel.onAction(DiscoverAction.OnStart)
                speechRecognizer.startListening(
                    onResult = { recognizedText ->
                        viewModel.onAction(
                            DiscoverAction.OnQueryFound(recognizedText)
                        )

                    },
                    onError = {
                        viewModel.onAction(DiscoverAction.OnCancel)
                    }
                )
            }) {

            }
            if (state.isListening) {
                Text(text = "Listening")
            }
            if (state.isLoading) {
                Text(text = "Recognizing")
            }
        }
    }
}