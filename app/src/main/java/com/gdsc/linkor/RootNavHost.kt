package com.gdsc.linkor

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.gdsc.linkor.ui.tutorlist.TutorDetailScreen
import com.gdsc.linkor.ui.tutorlist.TutorListScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RootNavHost(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Graph.TUTOR) {
        tutorListGraph(navController)
    }
}

object Graph {
    const val TUTOR = "tutor_graph"
}

fun NavGraphBuilder.tutorListGraph(navController: NavController) {
    navigation(startDestination = "tutor_list", route = Graph.TUTOR) {
        composable("tutor_list") { TutorListScreen(navController)
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
