package com.gdsc.linkor.ui.mypage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gdsc.linkor.R
import com.gdsc.linkor.SignInViewModel
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.question.Gender
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun Mypage(
    questionViewModel: QuestionViewModel ,
    signInViewModel: SignInViewModel
){

    Column(
        modifier=Modifier
            .fillMaxSize()
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
                    fontSize = 30.sp,
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
                        .padding(horizontal = 35.dp)
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(60.dp)
                        .padding(horizontal = 25.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                ) {


                }
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
                fontSize = 23.sp,
                color = Color(android.graphics.Color.parseColor("#4C6ED7")),
                modifier = Modifier
                    .padding(0.dp)

            )

        }


    }

}








/*
@Preview(showBackground = true)
@Composable
fun MypagePreview() {



       Mypage()

}*/
