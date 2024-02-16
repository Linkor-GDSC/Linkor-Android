package com.gdsc.linkor.ui.learning

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdsc.linkor.R
import com.gdsc.linkor.TTSManager
import com.gdsc.linkor.data.SentenceData
import com.gdsc.linkor.ui.component.GoBackButton


@Composable
fun LearningSentencesScreen(navController: NavController,ttsManager: TTSManager,step:String) {
    val space = 30.dp
    val sentenceData = SentenceData()
    //문장 리스트
    val sentenceList = remember{
        when(step){
            "Beginner" -> sentenceData.loadBasicSentences()
            "Intermediate" -> sentenceData.loadIntermediateSentences()
            "Advanced" -> sentenceData.loadAdvancedSentences()
            else -> emptyList()
        }.shuffled() //섞기
    }
    //현재 리스트 인덱스
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentWord = sentenceList[currentIndex]
    //현재 한국어 문장
    val korean = currentWord.korean
    //현재 영어 문장
    val english = currentWord.english

    val context = LocalContext.current

    Surface(
        color=Color(0xffFFFFFF),

    ) {
        //뒤로 가기 버튼
        GoBackButton(navController = navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center

        ) {
            Text(text="Sentences for $step",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(100.dp))
            //문장 카드
            LearningCard(korean = korean, english = english)
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                //왼쪽 버튼
                IconButton(onClick = {
                    //첫번째 문장일 경우 알림 띄우기
                    if (currentIndex==0){ Toast.makeText(context, "This is the first sentence.", Toast.LENGTH_SHORT).show() }
                    currentIndex = (currentIndex - 1).coerceIn(sentenceList.indices)
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_left), contentDescription = "left button")
                }
                Spacer(Modifier.width(space))
                //스피커 버튼
                IconButton(onClick = {
                    ttsManager.speak(korean)
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_listen), contentDescription = "speaker")
                }
                Spacer(Modifier.width(space))
                //오른쪽 버튼
                IconButton(onClick = {
                    //마지막 문장일 경우 알림 띄우기
                    if (currentIndex==sentenceList.size-1){ Toast.makeText(context, "This is the last sentence.", Toast.LENGTH_SHORT).show() }
                    currentIndex = (currentIndex + 1).coerceIn(sentenceList.indices)
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_right), contentDescription = "right button")
                }
            }
        }

    }

}

@Composable
fun LearningCard(modifier:Modifier=Modifier,korean:String,english:String){
    Card(
        modifier = modifier.size(300.dp)
            //.widthIn(min = 300.dp)
            .heightIn(min = 300.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color= Color(0xFF4C6ED7)),
        colors = CardDefaults.cardColors(containerColor=Color(0xFFFFFFFF))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier=modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = korean,
                color = Color(0xFF4C6ED7),
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier.height(30.dp))
            Text(text=english,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

    }

}

@Preview
@Composable
fun SentencesPreview(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val ttsManager=TTSManager(context)
    LearningSentencesScreen(navController = navController,ttsManager=ttsManager,step="Beginner")
}