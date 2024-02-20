package com.gdsc.linkor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.gdsc.linkor.ui.community.Post
import com.gdsc.linkor.ui.community.comment.commentScreen
import com.gdsc.linkor.ui.community.CommunityScreen
import com.gdsc.linkor.ui.community.CommunityViewmodel
import com.gdsc.linkor.ui.community.writing.WritingScreen


@Composable
fun communityNavHost(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Route.COMMUNITY) {
        communityListGraph(navController, viewmodel =CommunityViewmodel())
    }
}



fun NavGraphBuilder.communityListGraph(navController: NavController, viewmodel: CommunityViewmodel) {
    navigation(startDestination = "community_list", route = Route.COMMUNITY) {
        composable(Route.COMMUNITYLIST) { CommunityScreen(navController, viewModel = viewmodel)
        }
        composable("comment/{id}/{photoUrl}/{name}/{title}/{content}/{time}")
        {
            val post = Post(
                id = it.arguments?.getInt("id")?:0,
                //photoUrl = URLEncoder.encode(it.arguments?.getString("photoUrl"), StandardCharsets.UTF_8.toString()),
                photoUrl = it.arguments?.getString("photoUrl")?:"",
                name = it.arguments?.getString("name") ?: "",
                title = it.arguments?.getString("title") ?: "",
                content = it.arguments?.getString("content") ?: "",
                time = it.arguments?.getString("time")?: "",
            )

            commentScreen(navController = navController, post = post, viewmodel= viewmodel)
            //TutorDetailScreen(/*tutor = */navController=navController,name=name,gender=gender,locationGu=locationGu)
        }
        composable("writingScreenRoute"){
            WritingScreen(navController = navController, viewModel = viewmodel)
        }
    }
}
