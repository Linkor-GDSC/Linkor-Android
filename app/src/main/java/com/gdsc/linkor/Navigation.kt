package com.gdsc.linkor

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.gdsc.linkor.data.UserPreferencesDataStore
import com.gdsc.linkor.navigation.BottomNavItem
import com.gdsc.linkor.navigation.Route
import com.gdsc.linkor.setting.QuestionResultScreen
import com.gdsc.linkor.setting.QuestionRoute
import com.gdsc.linkor.navigation.communityListGraph
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.ui.learning.LearningScreen
import com.gdsc.linkor.ui.message.MessageScreen
import com.gdsc.linkor.ui.mypage.Mypage
import com.gdsc.linkor.ui.tutorlist.TutorDetailScreen
import com.gdsc.linkor.ui.tutorlist.TutorListScreen



@Composable
fun LinkorNavHost(userPreferencesDataStore: UserPreferencesDataStore){

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

    val signInViewModel = SignInViewModel()
    val questionViewModel = QuestionViewModel()


    NavHost(navController = navController,
        startDestination = Route.ROOT
    ) {
        //자동 로그인 구현
        composable(Route.ROOT) {
            LaunchedEffect(Unit) {
                userPreferencesDataStore.getEmail().collect { email ->
                    if (email.isNullOrEmpty()) {
                        // 이메일이 저장되어 있지 않은 경우, SIGNIN로 이동
                        navController.navigate(Route.SIGNIN) {
                            Log.d("MyTagNav", "It is new user")
                            // 뒤로 가기 스택에 ROOT 경로가 없도록 설정
                            popUpTo(Route.ROOT) { inclusive = true }
                        }
                    } else {
                        // 이메일이 저장되어 있는 경우, TutorListScreen으로 이동
                        navController.navigate(Route.TUTOR) {
                            // 뒤로 가기 스택에 ROOT 경로가 없도록 설정
                            popUpTo(Route.ROOT) { inclusive = true }
                            Log.d("MyTagNav", "It is existing memeber. Email: $email")
                        }
                    }
                }
            }
        }

        //로그인
        composable(Route.SIGNIN){
            SignInScreen(signInViewModel = signInViewModel, navController = navController, userPreferencesDataStore = userPreferencesDataStore)
        }

        composable(Route.QUESTION_ROUTE){
            QuestionRoute(
                onQuestionComplete = {
                    navController.navigate(Route.TUTOR){
                        popUpTo(Route.QUESTION_ROUTE){inclusive=true}
                    }},
                onNavUp = navController::navigateUp,

                )
        }

        /*  done 클릭 이후 보여지는 화면 연결 > 나중에 수정하면 될듯..
        *  > Question.kt 안의 QuestionResultScreen() 도 함께 수정해야함 */

        composable(Route.QUESTION_RESULTS_ROUTE) {
            /*QuestionResultScreen {
                navController.popBackStack(QUESTION_ROUTE, false)
            }*/

        }


        //튜터 리스트
        try {
            tutorListGraph(navController)
        } catch (e:Exception){
            Log.e("MYTAG","tutor list graph error",e)
        }


        //한국어 문장 학습
        composable(BottomNavItem.Learning.screenRoute) {
            LearningScreen(navController = navController)
        }
        //커뮤니티
        communityListGraph(navController)

        //마이페이지
        composable(BottomNavItem.MyPage.screenRoute) {
            Mypage(questionViewModel = questionViewModel, signInViewModel = signInViewModel, navController = navController)
        }

        composable("${Route.MESSAGE}/{otherUserName}"){
            val otherUserName = it.arguments?.getString("otherUserName")?:""
            MessageScreen(navController=navController,otherUserName = otherUserName)
        }
    }
}

fun NavGraphBuilder.tutorListGraph(navController: NavController) {
    navigation(startDestination = Route.TUTORLIST, route = Route.TUTOR) {
        composable(BottomNavItem.TutorList.screenRoute) { TutorListScreen(navController)
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