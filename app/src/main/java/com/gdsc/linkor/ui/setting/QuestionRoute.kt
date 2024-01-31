package com.gdsc.linkor.setting

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
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

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the survey: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        // Going back to the previous question in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}

private tailrec fun Context.findActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }