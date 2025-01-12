package com.pablodev.shamovie.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pablodev.shamovie.core.presentation.RadioButtonGroup
import com.pablodev.shamovie.core.presentation.ShamovieButton
import com.pablodev.shamovie.core.presentation.SnackbarAction
import com.pablodev.shamovie.core.presentation.SnackbarController
import com.pablodev.shamovie.core.presentation.SnackbarEvent
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.core.util.toJson
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.media.presentation.discover.DiscoverAction
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.navigation.Route
import com.pablodev.shamovie.speech.SpeechRecognizer
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues,
    context: Context = LocalContext.current
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val speechRecognizer = SpeechRecognizer(context)
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when(state.mediaOption) {
                    MediaKey.MOVIE -> "Say The Movie"
                    MediaKey.TV_SHOW -> "Say The TV Show"
                },
                style = TextStyle(
                    fontSize = 28.sp
                )
            )
            Spacer(modifier = Modifier.height(48.dp))
            ShamovieButton(
                animate = when {
                    state.isListening -> true
                    state.isLoading -> false
                    else -> false
                },
                onClick = {
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
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (!state.isListening && !state.isLoading) {
                RadioButtonGroup(
                    selectedOption = state.mediaOption,
                    onOptionSelected = {
                        viewModel.onAction(DiscoverAction.OnMediaOptionChanged(it))
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(16.dp)
                )
            } else {
                Text(
                    text = when {
                        state.isListening -> "Listening..."
                        state.isLoading -> "Recognizing..."
                        else -> ""
                    },
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            state.mediaResult?.let { media ->
                state.query?.let { query ->

                    navController.navigate(
                        Route.Details(
                            query = query,
                            media = media.toJson(),
                            isMovie = true
                        )
                    )
                    viewModel.onAction(DiscoverAction.ResetResult)
                }
            }

            state.errorMessage?.let { error ->
                val msg = error.asString()
                var action: SnackbarAction? = null

                if (state.noResults == true) {
                    action = SnackbarAction(
                        name = "Modify",
                        action = {

                        }
                    )
                }

                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = msg,
                            action = action
                        )
                    )
                }
                viewModel.onAction(DiscoverAction.ResetResult)
            }
        }
    }
}

