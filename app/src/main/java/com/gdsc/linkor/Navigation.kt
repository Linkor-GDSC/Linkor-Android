package com.gdsc.linkor

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.gdsc.linkor.Destinations.QUESTION_RESULTS_ROUTE
import com.gdsc.linkor.Destinations.QUESTION_ROUTE
import com.gdsc.linkor.navigation.BottomNavItem
import com.gdsc.linkor.navigation.Route
import com.gdsc.linkor.setting.QuestionResultScreen
import com.gdsc.linkor.setting.QuestionRoute
import com.gdsc.linkor.ui.learning.LearningScreen
import com.gdsc.linkor.ui.tutorlist.TutorDetailScreen
import com.gdsc.linkor.ui.tutorlist.TutorListScreen

object Destinations{

    const val QUESTION_ROUTE = "question"
    const val QUESTION_RESULTS_ROUTE = "questionresults"
}
/*object Graph {
    const val TUTOR = "tutor_graph"
}*/

@Composable
fun LinkorNavHost(){

    val navController: NavHostController = rememberNavController()
    /*NavHost(
        navController = navController,
        *//*   첫화면  *//*
        startDestination = QUESTION_ROUTE,
    ){
        composable(QUESTION_ROUTE){
            QuestionRoute(
                onQuestionComplete = { navController.navigate(QUESTION_RESULTS_ROUTE)},
                onNavUp = navController::navigateUp,

            )
        }

        *//*  done 클릭 이후 보여지는 화면 연결 > 나중에 수정하면 될듯..
        *  > Question.kt 안의 QuestionResultScreen() 도 함께 수정해야함 *//*

        composable(QUESTION_RESULTS_ROUTE) {
            QuestionResultScreen {
                navController.popBackStack(QUESTION_ROUTE, false)
            }
        }

    }*/

    NavHost(navController = navController, startDestination = Route.TUTOR) {

        try {
            tutorListGraph(navController)
        } catch (e:Exception){
            Log.e("MYTAG","tutor list graph error",e)
        }


        composable(BottomNavItem.Learning.screenRoute) {
            LearningScreen(navController = navController)
        }
        composable(BottomNavItem.Community.screenRoute) {
        }
        composable(BottomNavItem.MyPage.screenRoute) {

        }
    }
}

fun NavGraphBuilder.tutorListGraph(navController: NavController) {
    navigation(startDestination = Route.TUTORLIST, route = Route.TUTOR) {
        composable(Route.TUTORLIST) { TutorListScreen(navController)
        }
        composable("tutor_detail/{photoUrl}/{name}/{gender}/{locationGu}/{locationSido}/{time}/{tutoringMethod}/{introduction}")
        {
            val tutor = Tutor(
                //photoUrl = URLEncoder.encode(it.arguments?.getString("photoUrl"), StandardCharsets.UTF_8.toString()),
                photoUrl = it.arguments?.getString("photoUrl")?:"",
                name = it.arguments?.getString("name") ?: "",
                gender = it.arguments?.getString("gender") ?: "",
                locationGu = it.arguments?.getString("locationGu") ?: "",
                locationSido = it.arguments?.getString("locationSido") ?: "",
                time = it.arguments?.getString("time") ?: "",
                tutoringMethod = it.arguments?.getString("tutoringMethod")?:"",
                introduction = it.arguments?.getString("introduction")?:""
            )

            TutorDetailScreen(navController = navController, tutor = tutor)
            //TutorDetailScreen(/*tutor = */navController=navController,name=name,gender=gender,locationGu=locationGu)
        }
    }
}