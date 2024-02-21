package com.gdsc.linkor.ui.mypage.Edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.linkor.setting.QuestionViewModel

@Composable
fun introEdit(
    viewModel: QuestionViewModel,
){
   val localFocusManager = LocalFocusManager.current

    Surface(
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = Color.Transparent
        ),

        //박스 크기 조절
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
    ) {

        OutlinedTextField(
            value = viewModel.intro,
            onValueChange = { viewModel.intro = it },
            enabled = true,
            shape = MaterialTheme.shapes.medium,
            textStyle = TextStyle(fontSize = 18.sp,
            ),
            colors = OutlinedTextFieldDefaults.colors( //border 색, label 색 변경
                focusedBorderColor = Color(android.graphics.Color.parseColor("#4C6ED7")), // 포커스를 얻었을 때 outline 색상 지정
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(android.graphics.Color.parseColor("#4C6ED7")),
                unfocusedLabelColor = Color.LightGray
            ),
            label = { Text(text = "about me") },

            /*  자기소개 작성 완료 버튼 누르면 커서 안보이게  */

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions{
                localFocusManager.clearFocus()
            },

            //내부 변경
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .background(Color.Transparent),

            )
    }
}