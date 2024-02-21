package com.gdsc.linkor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.gdsc.linkor.ui.community.comment.commentScreen
import com.gdsc.linkor.ui.community.CommunityScreen
import com.gdsc.linkor.ui.community.CommunityViewmodel
import com.gdsc.linkor.ui.community.data.post.Post2
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
        /*composable("comment/{id}/{writerPhotoUrl}/{writer}/{title}/{content}/{createdAt}")
        {
            val post = Post2(
                id = it.arguments?.getInt("id"),
                //photoUrl = URLEncoder.encode(it.arguments?.getString("photoUrl"), StandardCharsets.UTF_8.toString()),
                writerPhotoUrl = it.arguments?.getString("writerPhotoUrl")?:"",
                writer = it.arguments?.getString("writer") ?: "",
                title = it.arguments?.getString("title") ?: "",
                content = it.arguments?.getString("content") ?: "",
                createdAt = it.arguments?.getString("createdAt")?: "",
            )

            commentScreen(navController = navController, post = post, viewmodel= viewmodel)

        }*/
        composable("writingScreenRoute"){
            WritingScreen(navController = navController, viewModel = viewmodel)
        }
        composable(
            route = "comment/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                commentScreen(navController = navController, viewmodel = viewmodel,id=id)
                //commentScreen(navController = navController, id=id,viewmodel=viewmodel)
            }
        }
        /*composable(
            route = "comment"
        ){
            commentScreen(navController = navController, viewmodel=viewmodel)
        }*/
    }
}
