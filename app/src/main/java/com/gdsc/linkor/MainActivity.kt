package com.gdsc.linkor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.gdsc.linkor.data.UserPreferencesDataStore
import com.gdsc.linkor.navigation.LinkorNavHost
import com.gdsc.linkor.ui.theme.LinkorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkorTheme {

                val context = LocalContext.current
                val userPreferencesDataStore = remember { UserPreferencesDataStore(context) }

                LinkorNavHost(userPreferencesDataStore)


            }
        }
    }
}