package com.gdsc.linkor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gdsc.linkor.ui.theme.LinkorTheme
import com.gdsc.linkor.ui.tutorlist.TutorDetailScreen
import com.gdsc.linkor.ui.tutorlist.TutorListScreen
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkorTheme {
                //SignInScreen()
                //TutorListScreen()
                //TutorDetailScreen(Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Nunsong Kim","Woman","Seoul","Yong San","MON,TUE","FTF","Hello nice to meet you. I'll teach you how to pronounce Korean by listening k-pop"))
                RootNavHost()

                //LinkorNavHost()
            }
        }
    }
}