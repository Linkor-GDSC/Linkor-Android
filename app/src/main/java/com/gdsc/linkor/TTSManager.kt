package com.gdsc.linkor

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TTSManager(context: Context) : TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech = TextToSpeech(context, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MYTAGTTSManager", "Language not supported")
            } else {
                isInitialized = true
            }
        } else {
            Log.e("MYTAGTTSManager", "Initialization failed")
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Log.e("TTSManager", "TextToSpeech is not initialized")
        }
    }

    fun shutdown() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        isInitialized = false
    }
}