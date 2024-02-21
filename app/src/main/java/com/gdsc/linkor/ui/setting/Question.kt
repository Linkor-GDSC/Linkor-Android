package com.gdsc.linkor.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.question.Communication
import com.gdsc.linkor.setting.question.CommunicationMethod
import com.gdsc.linkor.setting.question.Gender
import com.gdsc.linkor.setting.question.Introduction
import com.gdsc.linkor.setting.question.Location

@Composable
fun ModeQuestion(
    selectedAnswers: Mode?,
    onOptionSelected: (Mode) -> Unit,
    modifier: Modifier = Modifier
){
    val possibleAnswers = listOf(
        Mode(R.string.student),
        Mode(R.string.tutor),

        )

    Mode(
        titleResourceId = R.string.titleMode,
        possibleAnswers = possibleAnswers,
        selectedAnswer = selectedAnswers,
        onOptionSelected = onOptionSelected,
    )
}


@Composable
fun GenderQuestion(
    selectedAnswers: Gender?,
    onOptionSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier
){
    Gender(
        titleResourceId = R.string.titleGender,
        possibleAnswers = listOf(
            Gender(R.string.Woman),
            Gender(R.string.Man),
            Gender(R.string.Other),
        ),
        selectedAnswer = selectedAnswers,
        onOptionSelected = onOptionSelected,
        modifier = Modifier,
    )

}

@Composable
fun LocationQuestion(
    viewModel: QuestionViewModel,
    modifier: Modifier = Modifier
){
    Location(
        titleResourceId = R.string.titleLocation,
        viewModel = viewModel
    )
}

@Composable
fun CommunicationQuestion(
    selectedAnswers: Communication?,
    onOptionSelected: (Communication) -> Unit,
    modifier: Modifier = Modifier
){
    val possibleAnswers = listOf(
        Communication(R.string.FTF),
        Communication(R.string.NFTF),

        )

    CommunicationMethod(
        titleResourceId = R.string.titleCommunication,
        possibleAnswers = possibleAnswers,
        selectedAnswer = selectedAnswers,
        onOptionSelected = onOptionSelected,
    )
}

@Composable
fun IntroductionQuestion(
    modifier: Modifier = Modifier,
){
    Introduction(titleResourceId =R.string.titleIntro)
}

