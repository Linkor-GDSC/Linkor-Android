package com.example.linkor.setting

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.linkor.R
import com.example.linkor.setting.util.supportWideScreen

@Composable
fun QuestionScreen(
    questionScreenData: questionScreenData,
    isNextEnabled: Boolean,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
){
    Surface(
        modifier = Modifier
            .supportWideScreen()
    ) {
        Scaffold (
            content = content,
            bottomBar = {
                QuestionBottomBar(
                    shouldShowPreviousButton = questionScreenData.shouldShowPreviousButton,
                    shouldShowDoneButton = questionScreenData.shouldShowDoneButton,
                    isNextButtonEnabled = isNextEnabled,
                    onPreviousPressed = onPreviousPressed,
                    onNextPressed = onNextPressed,
                    onDonePressed = onDonePressed

                )
            }
        )
    }

}

/* 질문들 끝나고 다음  마지막 화면 - 전체 구성 */

@Composable
fun QuestionResultScreen(
    onDonePressed: () -> Unit
){
    Surface(
        modifier = Modifier.supportWideScreen()
    ) {
        Scaffold (
            content = {
                innerPadding ->
                QuestionResult(
                    title = stringResource(id = R.string.titleLocation),
                    modifier = Modifier.padding(innerPadding)
                )
            },
            bottomBar = {
                OutlinedButton(
                    onClick = onDonePressed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ){
                    Text(text = stringResource(id = R.string.titleGender))
                }
            }
        )
    }
}

/* 질문들 끝나고 다음  마지막 화면 - 세부 구성 */

@Composable
private fun QuestionResult(
    title: String,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        item{
            Spacer(modifier = Modifier.height(44.dp))
            Text(text = title,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 20.dp)
            )



        }
    }
}


@Composable
fun QuestionBottomBar(
    shouldShowPreviousButton: Boolean,
    shouldShowDoneButton: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
){
    Surface(shadowElevation = 7.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // Since we're not using a Material component but we implement our own bottom bar,
                // we will also need to implement our own edge-to-edge support. Similar to the
                // NavigationBar, we add the horizontal and bottom padding if it hasn't been consumed yet.
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (shouldShowPreviousButton) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onPreviousPressed,
                    border = BorderStroke(1.dp, Color(android.graphics.Color.parseColor("#4C6ED7"))),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(android.graphics.Color.parseColor("#4C6ED7")), // 텍스트 색상 변경
                    )
                ) {
                    Text(text = stringResource(id = R.string.previous))
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (shouldShowDoneButton) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onDonePressed,
                   // enabled = isNextButtonEnabled, -> 선택지 선택 시 next 보이게
                ) {
                    Text(text = stringResource(id = R.string.done))
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onNextPressed,
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White, // 텍스트 색상 변경
                        containerColor = Color(android.graphics.Color.parseColor("#4C6ED7")) //배경색 색상 변경
                    ),
                   // enabled = isNextButtonEnabled,
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}