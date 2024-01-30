package com.gdsc.linkor

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gdsc.linkor.Destinations.QUESTION_RESULTS_ROUTE
import com.gdsc.linkor.Destinations.QUESTION_ROUTE
import com.gdsc.linkor.setting.QuestionResultScreen
import com.gdsc.linkor.setting.QuestionRoute

object Destinations{
    const val QUESTION_ROUTE = "question"
    const val QUESTION_RESULTS_ROUTE = "questionresults"
}

@Composable
fun LinkorNavHost(
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        /*   첫화면  */
        startDestination = QUESTION_ROUTE,
    ){
        composable(QUESTION_ROUTE){
            QuestionRoute(
                onQuestionComplete = { navController.navigate(QUESTION_RESULTS_ROUTE)},
                onNavUp = navController::navigateUp,

            )
        }

        /*  done 클릭 이후 보여지는 화면 연결 > 나중에 수정하면 될듯..
        *  > Question.kt 안의 QuestionResultScreen() 도 함께 수정해야함 */

        composable(QUESTION_RESULTS_ROUTE) {
            QuestionResultScreen {
                navController.popBackStack(QUESTION_ROUTE, false)
            }
        }

    }
}