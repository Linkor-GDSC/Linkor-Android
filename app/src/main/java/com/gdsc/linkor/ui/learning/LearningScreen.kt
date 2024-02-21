package com.gdsc.linkor.ui.learning

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.linkor.navigation.LinkorBottomNavigation
import com.gdsc.linkor.navigation.Route

val steps = listOf(
    listOf("Beginner","basic"),
    listOf("Intermediate","intermediate"),
    listOf("Advanced", "advanced")
    )

val colors = listOf(
    Color(0xFF3B8700),
    Color(0xFF005687),
    Color(0xFF4C0087)
)
@Composable
fun LearningScreen(navController:NavController){

    Scaffold(bottomBar = { LinkorBottomNavigation(navController = navController) },
    ) {
        Surface(Modifier.padding(it),
            color=Color(0xffFFFFFF)
        ){
            Column(
                Modifier
                    .padding(horizontal = 15.dp, vertical = 40.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ){
                for (i in steps.indices) {
                    LearningStep(step = steps[i][0], stepSmall = steps[i][1],color=colors[i]){
                        navController.navigate("${Route.LEARNING_SENTENCES}/${steps[i][0]}")
                    }
                }

            }

        }
    }
}

@Composable
fun LearningStep(modifier: Modifier=Modifier,step:String ,stepSmall:String,color: Color,onClick:()->Unit){
    val stepSize = 18.sp

    Surface(
        onClick = { onClick() },
        modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        color= Color(0xFFFFFFFF),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE0E0E0))
    ){
        Column(
            modifier=modifier.padding(start=20.dp,end=10.dp,top=20.dp,bottom=50.dp)
        ) {
            Row(){
                Text(text = "For ", fontSize = stepSize)
                Text(
                    text= step,
                    color = color,
                    fontSize = stepSize
                )
            }
            Spacer(modifier = modifier.height(10.dp))
            Text(text="Learn about $stepSmall level sentences",
                color=Color(0xFF585858),
                fontSize = 12.sp
                )
        }
    }
}

@Preview
@Composable
fun Preview(){
    val navController = rememberNavController()
    LearningScreen(navController = navController)
    //LearningStep()
}