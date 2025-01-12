package com.pablodev.shamovie

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.pablodev.shamovie.navigation.RootNavGraph
import com.pablodev.shamovie.ui.theme.ShamovieTheme

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Initialize the Activity Result API launcher
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Log.d("MainActivity", "Microphone permission granted")
            } else {
                Log.d("MainActivity", "Microphone permission denied")
            }
        }

        // Check and request microphone permission
        if (!isMicrophonePermissionGranted()) {
            requestMicrophonePermission()
        }


        setContent {
            ShamovieTheme {
                RootNavGraph()
            }
        }
    }

    // Check if the microphone permission is granted
    private fun isMicrophonePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request the microphone permission
    private fun requestMicrophonePermission() {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
}
