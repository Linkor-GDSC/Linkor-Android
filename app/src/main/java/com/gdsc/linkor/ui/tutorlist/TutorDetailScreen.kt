package com.gdsc.linkor.ui.tutorlist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.model.Tutor
import com.gdsc.linkor.navigation.Route
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun TutorDetailScreen(navController: NavController,
                      /*name:String,
                      gender:String,
                      locationGu:String,
                      locationSido:String,
                      time:String,
                      tutoringMethod:String*/
                      //tutor: Tutor,
                      email:String,
                      viewModel: TutorListViewModel = hiltViewModel()
) {


    viewModel.getTutorDetail(email = email)
    val tutor = viewModel.tutorDetail.value.tutor


    //뒤로 가기 버튼
    IconButton(onClick = { navController.navigateUp() }) {
        Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "arrow left",
            modifier = Modifier
                .padding(8.dp),
            alignment = Alignment.TopStart)
    }

    Column {
        //튜터 프로필, 이름
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //프로필 사진
            AsyncImage(
                model = tutor.photoUrl,
                "tutor profile",
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.height(10.dp))
            //튜터 이름
            Text(text = tutor.name)
        }

        Spacer(modifier = Modifier.height(35.dp))

        //성별, 장소, 날짜, 대면/비대면
        Column(modifier = Modifier.padding(horizontal = 45.dp)){
            Row() {
                Column {
                    Text(text = "Gender", color = Color(0xFF979797))
                    Text(text = "Location", color = Color(0xFF979797))
                    Text(text = "Time", color = Color(0xFF979797))
                    Text(text = "FTF/NFTF", color = Color(0xFF979797))
                }
                Spacer(modifier = Modifier.width(50.dp))
                Column {
                    Text(text = tutor.gender)
                    Text(text = tutor.locationGu + ", " + tutor.locationSido)

                    Text(text = tutor.time.joinToString())
                    Text(text = tutor.tutoringMethod)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Text(text = "About me", color = Color(0xFF979797))
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = tutor.introduction)
            }

        }
    }
    Column(modifier= Modifier
        .fillMaxHeight()
        .padding(vertical = 30.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Bottom) {
        //채팅 버튼
        SendMessageButton {
            try {
                val photoUrl = if (tutor.photoUrl.isNotEmpty()) URLEncoder.encode(tutor.photoUrl, StandardCharsets.UTF_8.toString()) else tutor.photoUrl
                navController.navigate("${Route.MESSAGE}/${tutor.name}/${tutor.email}/${photoUrl}")
                Log.d("mytag tutordetail","${photoUrl}")
                Log.d("mytag tutordetail","${tutor.photoUrl}")
            }catch (e:Exception){
                Log.e("MYTAG tutordetailscreen","채팅 화면 이동 실패",e)
            }
        }
    }

}

@Composable
fun SendMessageButton(onClick: () -> Unit){
    Button(onClick = onClick ,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4C6ED7),
            contentColor = Color(0xFFFDFDFD),),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.send_message),
            fontSize = 16.sp

        )
    }
}

@Composable
@Preview
fun TutorDetailScreenPreview(){
    val navController: NavController
    //TutorDetailScreen(navController = navController,Tutor("Nunsong Kim","Woman","Seoul","Yong San","MON, TUE","FTF","Hello nice to meet you. I'll teach you how to pronounce Korean by listening k-pop"))
}