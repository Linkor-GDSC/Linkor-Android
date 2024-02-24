package com.gdsc.linkor.ui.mypage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.model.Tutee
import com.gdsc.linkor.ui.signin.SignInViewModel
import com.gdsc.linkor.navigation.LinkorBottomNavigation
import com.gdsc.linkor.navigation.Route
import com.gdsc.linkor.setting.QuestionViewModel
import com.google.firebase.auth.FirebaseAuth
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun Mypage(
    questionViewModel: QuestionViewModel,
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavController,
    myPageViewModel: MyPageViewModel= hiltViewModel()
){
    signInViewModel.getEmail()?.let { myPageViewModel.getTutee(email = it) }
    val tuteeList by myPageViewModel.tuteeList.collectAsState()

    Scaffold (
        bottomBar = { LinkorBottomNavigation(navController = navController) },
        containerColor = Color.White
    )
    {
        Column(
            modifier= Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(it)
        ) {
            Spacer(Modifier.height(50.dp))

            val Name = signInViewModel.getName()
            val Photo = signInViewModel.getImage()

            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier

                ) {
                    Text(text = "Hello, $Name !",
                        fontStyle = FontStyle.Normal,
                        fontSize = 25.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 35.dp)
                            .wrapContentSize()
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(text ="Sharing your knowlege \n" +
                            "makes the world more beautiful!",
                        fontStyle = FontStyle.Normal,
                        fontSize = 15.sp,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(start = 35.dp)
                    )
                }


                if (Photo != null) {
                    ImageUri(uri = Photo)
                }else{

                    Box(modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        // .background(Color.DarkGray)

                    )
                    {
                        // 이미지 변경 가능 -> 화질이 깨져서..

                        Image(painter = painterResource(id = R.drawable.settingimage),
                            contentDescription =null,
                            modifier = Modifier.fillMaxSize()

                        )
                    }
                }


            }



            Spacer(Modifier.height(30.dp))

            Surface(
                //   shadowElevation = 2.dp,
                shape = MaterialTheme.shapes.small,
                color = Color.Transparent,
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Transparent
                ),

                //박스 크기 조절
                modifier = Modifier
                    .padding(horizontal = 35.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = "Edit Profile",
                        onValueChange = {""},
                        enabled = false,
                        shape = MaterialTheme.shapes.small,
                        textStyle = TextStyle(fontSize = 18.sp,
                        ),
                        trailingIcon = {
                            var isBottomSheetVisible by remember { mutableStateOf(false) }
                            // Icon to be displayed after the input text
                            IconButton(onClick = {     isBottomSheetVisible = true},
                                modifier = Modifier
                                    //    .size(60.dp)
                                    .padding(horizontal = 5.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Icon",
                                    tint = Color(android.graphics.Color.parseColor("#4C6ED7")),
                                    modifier = Modifier
                                        .fillMaxWidth()


                                )

                            }
                            if (isBottomSheetVisible) {

                                BottomSheetDemo(
                                    viewModel= questionViewModel,
                                ) {
                                    isBottomSheetVisible = false
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors( //border 색, label 색 변경
                            unfocusedLabelColor = Color.LightGray
                        ),
                        //내부 변경
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                            .background(Color(android.graphics.Color.parseColor("#F2F2F2"))),

                        )

                }

            }

            Spacer(Modifier.height(30.dp))

            Surface(
                shadowElevation = 4.dp,
                shape = MaterialTheme.shapes.small,
                color = Color(android.graphics.Color.parseColor("#6296DB")),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Transparent
                ),

                //박스 크기 조절
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .padding(horizontal = 35.dp)
            ) {
                Column(

                ) {
                    Row {
                        Text(text = "My Students",
                            fontStyle = FontStyle.Normal,
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(25.dp)
                        )

                    }


                    //튜티 목록
                    LazyColumn(
                        contentPadding = PaddingValues(end = 15.dp), // 전체 컨텐츠 하단에 패딩
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(tuteeList) { tutee ->
                            TuteeItem(tutee=tutee){
                                val photoUrl = if (tutee.photoUrl.isNotEmpty()) URLEncoder.encode(tutee.photoUrl, StandardCharsets.UTF_8.toString()) else tutee.photoUrl
                                navController.navigate("${Route.MESSAGE}/${tutee.name}/${tutee.email}/${photoUrl}")
                            }
                            /*Text(text = "이름: ${tutee.name}")
                            Text(text = "이메일: ${tutee.email}")*/
                        }
                    }
                    /*Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(60.dp)
                            .padding(horizontal = 25.dp)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                    ) {


                    }*/
                }


            }

            //  바텀 네비게이션 바 넣고 수정가능 (로그아웃 버튼 위치)
            Spacer(Modifier.height(100.dp))


            Button(onClick = {
                // 로그아웃 버튼 클릭 시 FirebaseAuth에서 로그아웃
                FirebaseAuth.getInstance().signOut()

                // ViewModel 상태 업데이트
                signInViewModel.clearUserData() },

                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors( Color.Transparent),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),



                ) {
                Text(text = "LOG OUT",
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp,
                    color = Color(android.graphics.Color.parseColor("#4C6ED7")),
                    modifier = Modifier
                        .padding(0.dp)

                )

            }


        }
    }


}

@Composable
fun TuteeItem(
    tutee: Tutee,
    onClick:(Tutee)->Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp)
            .padding(horizontal = 25.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White, /*shape = RoundedCornerShape(10.dp)*/),
        onClick = {onClick(tutee)}
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(10.dp))
            AsyncImage(
                model =tutee.photoUrl,
                contentDescription = "Tutee Profile Image",
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(RoundedCornerShape(50))
                    //.padding(start=5.dp)
            )
            //Spacer(Modifier.width(10.dp))

            Text(text = tutee.name,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)

                

            )
            //Text(text="HyunJung")
        }

    }

}










/*
@Preview(showBackground = true)
@Composable
fun MypagePreview() {



       Mypage()

}*/
