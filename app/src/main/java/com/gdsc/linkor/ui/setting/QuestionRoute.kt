package com.gdsc.linkor.setting

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

private const val CONTENT_ANIMATION_DURATION = 300

/**
 * Displays a [QuestionsScreen] tied to the passed [QuestionViewModel]
 */

@Composable
fun QuestionRoute(
    onQuestionComplete: () -> Unit,
    onNavUp: () -> Unit,
){
    val viewModel: QuestionViewModel = viewModel(
        factory = QuestioniewModelFactory()
    )

    val questionScreenData = viewModel.questionScreenData ?: return

    BackHandler {
        if(!viewModel.onBackPressed()){
            onNavUp()
        }
    }

    QuestionScreen(
        viewModel = viewModel,
        questionScreenData = questionScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = {
            onNavUp()
        },
        onPreviousPressed = { viewModel.onPreviousPressed()},
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(onQuestionComplete)}
    ){paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = questionScreenData,
            label = "surveyScreenDataAnimation"
        ){targetState ->
            when (targetState.Questions){
                Questions.Mode -> {
                    ModeQuestion(
                        selectedAnswers = viewModel.ModeResponse,
                        onOptionSelected = viewModel::onModeResponse,
                        modifier = Modifier,
                    )
                }

                Questions.Gender -> {
                    GenderQuestion(
                        selectedAnswers = viewModel.GenderResponse,
                        onOptionSelected = viewModel::onGenderResponse,
                        modifier = Modifier,
                    )
                }

                Questions.Location -> {
                    LocationQuestion(
                        viewModel =viewModel,
                        modifier = Modifier,
                    )
                }

                Questions.Communication -> {
                    CommunicationQuestion(
                        selectedAnswers = viewModel.CommunicationResponse,
                        onOptionSelected = viewModel::onCommunicationResponse,
                        modifier = Modifier,
                    )
                }

                Questions.Introduction -> {
                    IntroductionQuestion(
                        modifier = Modifier,
                    )
                }


            }

        }

    }

}


