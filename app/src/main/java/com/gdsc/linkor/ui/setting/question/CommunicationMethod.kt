package com.gdsc.linkor.setting.question


import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.QuestionWrapper

data class Communication(@StringRes val stringResourceId: Int)

@Composable
fun CommunicationMethod(
    @StringRes titleResourceId: Int,
    possibleAnswers: List<Communication>,
    selectedAnswer: Communication?,
    onOptionSelected: (Communication) -> Unit,
    modifier: Modifier = Modifier,
) {
        QuestionWrapper(
            viewModel = QuestionViewModel(),
        titleResourceId = titleResourceId,
        modifier = modifier
            .selectableGroup()
            .fillMaxSize(),
    ) {
        Text(
            text = "Communication Method",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)

        )

        Spacer(Modifier.height(120.dp))

        possibleAnswers.forEach {
            val selected = it == selectedAnswer

            NFT(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = stringResource(id = it.stringResourceId),

                selected = selected,
                onOptionSelected = { onOptionSelected(it) }
            )
        }
    }
}

@Composable
fun NFT(
    text: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.Transparent,

        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                Color(android.graphics.Color.parseColor("#4C6ED7"))
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),

        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(MaterialTheme.shapes.small)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton,
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text,
                Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (selected) {
                        Color(android.graphics.Color.parseColor("#4C6ED7"))
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunicationMethodPreview() {
    val possibleAnswers = listOf(
        Communication(R.string.FTF),
        Communication(R.string.NFTF),

    )
    var selectedAnswer by remember { mutableStateOf<Communication?>(null) }

    CommunicationMethod(
        titleResourceId = R.string.titleCommunication,
        possibleAnswers = possibleAnswers,
        selectedAnswer = selectedAnswer,
        onOptionSelected = { selectedAnswer = it },
    )
}
