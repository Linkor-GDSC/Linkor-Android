package com.gdsc.linkor.ui.community.writing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.QuestioniewModelFactory
import com.gdsc.linkor.ui.community.CommunityViewmodel
import com.gdsc.linkor.ui.community.comment.Postcomment
import com.gdsc.linkor.ui.community.comment.getComment

@Composable
fun WritingScreen(
    navController: NavController,
    viewModel: CommunityViewmodel = viewModel(),
){
    fun sendPost() {
        viewModel.sendPost(viewModel.titleWriting, viewModel.ContentWriting)
        viewModel.titleWriting = ""
        viewModel.ContentWriting=""
    }


    Column {
        Row {
            //뒤로 가기 버튼
            IconButton(onClick = { navController.navigateUp() }) {
                Image(painter = painterResource(id = R.drawable.close), contentDescription = "arrow left",
                    modifier = Modifier
                        .padding(8.dp),
                    alignment = Alignment.TopStart)
            }

            Spacer(modifier = Modifier.weight(1f))

            //게시하기 버튼
            ElevatedButton(onClick = { sendPost(); navController.navigateUp() },
                elevation = ButtonDefaults.buttonElevation(3.dp,3.dp,3.dp,3.dp,3.dp),
                colors =  ButtonDefaults.buttonColors( Color(0xFF4C6ED7)),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(horizontal = 10.dp,)
                    .height(37.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Post", color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

            }

        }


        Context(viewModel = viewModel)



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Context(
    viewModel: CommunityViewmodel
){
    val localFocusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {

        //제목
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .defaultMinSize(minHeight = 20.dp),
            value = viewModel.titleWriting,
            onValueChange = {viewModel.titleWriting = it },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                lineHeight = (17.7).sp,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions{
                localFocusManager.clearFocus()
            },
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                color = Color(0xFF4C6ED7),
                                start = Offset(
                                    x = 0f,
                                    y = size.height - 1.dp.toPx(),
                                ),
                                end = Offset(
                                    x = size.width,
                                    y = size.height - 1.dp.toPx(),
                                ),
                                strokeWidth = 1.dp.toPx(),
                            )

                        }
                ) {

                    if (viewModel.titleWriting.isEmpty()) {
                        TextFieldHint(
                            hintRes = R.string.user_community_writing_titlebox_content_placeholder,
                            fontSize = 16.sp,
                        )
                    }else{
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }


            },

            )
        Spacer(modifier = Modifier.height(8.dp))
        //내용
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .defaultMinSize(minHeight = 20.dp),
            value = viewModel.ContentWriting,
            onValueChange = {viewModel.ContentWriting = it },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                lineHeight = (17.7).sp,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions{
                localFocusManager.clearFocus()
            },
            decorationBox = { innerTextField ->
                Column(
                ) {

                    if (viewModel.ContentWriting.isEmpty()) {
                        TextFieldHint(
                            hintRes = R.string.user_community_writing_contentbox_content_placeholder,
                            fontSize = 16.sp,
                        )
                    }else{
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }


            },

            )


    }



}

@Composable
fun TextFieldHint(hintRes: Int, fontSize: TextUnit) {
    // 필요한 경우 힌트를 커스텀으로 구현
    Text(
        text = stringResource(id = hintRes),
        fontSize = fontSize,
        color = Color.Gray
    )
}
