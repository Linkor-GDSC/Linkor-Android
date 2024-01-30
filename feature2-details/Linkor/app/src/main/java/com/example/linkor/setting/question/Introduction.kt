package com.example.linkor.setting.question


import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkor.R
import com.example.linkor.setting.QuestionViewModel
import com.example.linkor.setting.QuestionWrapper


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Introduction(
    @StringRes titleResourceId: Int,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        titleResourceId = titleResourceId,
        modifier = modifier
            .selectableGroup()
            .fillMaxSize(),
    ) {

        Spacer(Modifier.height(20.dp)) //타이틀과 여백

        var text by remember { mutableStateOf("") }
        Surface(
            color = Color.Transparent,
            border = BorderStroke(
                width = 1.dp,
                color = Color.Transparent
            ),

            //박스 크기 조절
            modifier = Modifier
                .fillMaxWidth()
                .size(400.dp)
                .padding(16.dp)
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
                label = { Text(text = "about me")},
                //내부 변경
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .background(Color.Transparent),

            )
        }



    }
}



@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    Introduction(titleResourceId =R.string.titleIntro)
}