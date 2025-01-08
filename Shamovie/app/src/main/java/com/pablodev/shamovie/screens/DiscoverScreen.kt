package com.pablodev.shamovie.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pablodev.shamovie.R
import com.pablodev.shamovie.media.presentation.discover.DiscoverAction
import com.pablodev.shamovie.media.presentation.discover.DiscoverViewModel
import com.pablodev.shamovie.speech.SpeechRecognizer

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    navController: NavHostController,
    context: Context = LocalContext.current
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val speechRecognizer = SpeechRecognizer(context)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Say The Movie",
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
                            Toast.makeText(context, recognizedText, Toast.LENGTH_SHORT).show()
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
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = when {
                    state.isListening -> "Listening..."
                    state.isLoading -> "Recognizing..."
                    else -> ""
                },
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(48.dp))

        }
    }
}


@Composable
fun ShamovieButton(animate: Boolean = false, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Create pulsing effects with different scale factors for waves and icon
    val waveScales = List(5) { index ->
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = if (animate) 1.5f + (index * 0.3f) else 1f, // Animation starts only if animate is true
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = ""
        )
    }

    // The icon will pulse with its own unique scale
    val iconScale = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (animate) 1.2f else 1f, // Icon scales only if animation is triggered
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable (
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        // Only draw the waves if animation is active
        if (animate) {
            // Canvas with multiple circular waves growing
            Canvas(modifier = Modifier.fillMaxSize()) {
                val numWaves = 5
                val radiusStep = size.minDimension / numWaves

                val waveColors = listOf(
                    Color(0xFFFFE0B2).copy(alpha = 0.4f), // Very light orange with low opacity
                    Color(0xFFFFCC80).copy(alpha = 0.5f), // Light orange with low opacity
                    Color(0xFFFFB74D).copy(alpha = 0.6f), // Peachy orange with medium transparency
                    Color(0xFFFF9800).copy(alpha = 0.7f), // Bright orange with medium transparency
                    Color(0xFFFB8C00).copy(alpha = 0.8f)  // Slightly darker orange with high transparency
                )

                for (i in 0 until numWaves) {
                    val radius = (i + 1) * radiusStep * waveScales[i].value // Increase radius for each wave
                    drawCircle(
                        color = waveColors[i], // Use different shades of light orange
                        radius = radius,
                        center = center,
                        alpha = 1f - (i * 0.2f) // Decrease alpha for outer waves for fading effect
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.logo_shamovie),
            contentDescription = "Microphone Button",
            modifier = Modifier
                .size(180.dp)
                .scale(iconScale.value)
        )
    }
}


