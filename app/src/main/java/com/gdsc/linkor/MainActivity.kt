package com.gdsc.linkor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.gdsc.linkor.data.UserPreferencesDataStore
import com.gdsc.linkor.navigation.LinkorNavHost
import com.gdsc.linkor.ui.learning.LearningSentencesScreen
import com.gdsc.linkor.ui.theme.LinkorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var ttsManager: TTSManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttsManager=TTSManager(this)
        setContent {
            LinkorTheme {

                val context = LocalContext.current
                val userPreferencesDataStore = remember { UserPreferencesDataStore(this) }

                LinkorNavHost(userPreferencesDataStore,ttsManager)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsManager.shutdown()
    }
}