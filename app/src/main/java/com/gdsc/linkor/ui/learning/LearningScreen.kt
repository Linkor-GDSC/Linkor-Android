package com.gdsc.linkor.ui.learning

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.linkor.navigation.LinkorBottomNavigation

@Composable
fun LearningScreen(navController:NavController){

    Scaffold(bottomBar = { LinkorBottomNavigation(navController = navController) }) {
        Surface(Modifier.padding(it)){
            Text("learning")
        }
    }
}

@Preview
@Composable
fun Preview(){
    val navController = rememberNavController()
    LearningScreen(navController = navController)
}