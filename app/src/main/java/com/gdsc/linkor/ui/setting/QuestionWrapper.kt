package com.gdsc.linkor.setting

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//타이틀 - 선택지 전체 구성 파일

@Composable
fun QuestionWrapper(
    viewModel: QuestionViewModel,
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding( 16.dp)
          // .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(32.dp))
        QuestionTitle(titleResourceId)
        content()

    }

}

//질문 텍스트 스타일 변경
@Composable
private fun QuestionTitle(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = title),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp,
        color = Color.Black,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    )
}