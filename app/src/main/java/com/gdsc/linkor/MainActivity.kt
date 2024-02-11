package com.gdsc.linkor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.gdsc.linkor.data.UserPreferencesDataStore
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.ui.mypage.Mypage
import com.gdsc.linkor.ui.theme.LinkorTheme
import com.gdsc.linkor.ui.tutorlist.TutorDetailScreen
import com.gdsc.linkor.ui.tutorlist.TutorListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkorTheme {
                //SignInScreen()

                val context = LocalContext.current
                val userPreferencesDataStore = remember { UserPreferencesDataStore(context) }

                LinkorNavHost(userPreferencesDataStore)

            }
        }
    }
}