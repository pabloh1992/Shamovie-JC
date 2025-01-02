package com.pablodev.shamovie

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pablodev.shamovie.core.domain.onError
import com.pablodev.shamovie.core.domain.onSuccess
import com.pablodev.shamovie.media.domain.MediaRepository
import com.pablodev.shamovie.ui.theme.ShamovieTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val TAG = "MainActivity"

            val repository : MediaRepository = get()

            // Launch repository call in lifecycleScope
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

            ShamovieTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Shamovie"
                    )
                }
            }
        }
    }
}
