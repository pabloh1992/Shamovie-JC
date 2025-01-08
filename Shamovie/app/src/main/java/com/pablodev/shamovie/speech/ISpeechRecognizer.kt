package com.pablodev.shamovie.speech

interface ISpeechRecognizer {
    fun startListening(
        onResult: (String) -> Unit,
        onError: () -> Unit
    )
    fun stopListening()
}