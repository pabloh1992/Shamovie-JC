package com.pablodev.shamovie.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent

class SpeechRecognizer(private val context: Context) : ISpeechRecognizer {
    private val speechRecognizer = android.speech.SpeechRecognizer.createSpeechRecognizer(context)
    private val listener = object : android.speech.RecognitionListener {
        override fun onResults(results: Bundle?) {
            val spokenText = results?.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()
            spokenText?.let { onResult(it) }
        }

        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onEndOfSpeech() {
            speechRecognizer.stopListening()
        }
        override fun onError(error: Int) {
            onError()
        }
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
    }

    private lateinit var onResult: (String) -> Unit
    private lateinit var onError: () -> Unit

    override fun startListening(
        onResult: (String) -> Unit,
        onError: () -> Unit
        ) {
        this.onResult = onResult
        this.onError = onError
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        speechRecognizer.setRecognitionListener(listener)
        speechRecognizer.startListening(intent)
    }

    override fun stopListening() {
        speechRecognizer.stopListening()
    }
}