package com.example.linkor.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.linkor.R
import com.example.linkor.setting.question.Communication
import com.example.linkor.setting.question.CommunicationMethod
import com.example.linkor.setting.question.Gender
import com.example.linkor.setting.question.Introduction
import com.example.linkor.setting.question.Location2

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
    modifier: Modifier = Modifier
){
    Location2(
        titleResourceId = R.string.titleLocation,

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

