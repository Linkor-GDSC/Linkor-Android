package com.gdsc.linkor.ui.community

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.gdsc.linkor.ui.community.comment.commentScreen


@Composable
fun communityNavHost(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Graph.COMMUNITY) {
        communityListGraph(navController)
    }
}

object Graph {
    const val COMMUNITY = "community_graph"
}

fun NavGraphBuilder.communityListGraph(navController: NavController) {
    navigation(startDestination = "community_list", route = Graph.COMMUNITY) {
        composable("community_list") { communityScreen(navController)
        }
        composable("comment/{id}/{photoUrl}/{name}/{title}/{content}")
        {
            val post = Post(
                id = it.arguments?.getInt("id")?:0,
                //photoUrl = URLEncoder.encode(it.arguments?.getString("photoUrl"), StandardCharsets.UTF_8.toString()),
                photoUrl = it.arguments?.getString("photoUrl")?:"",
                name = it.arguments?.getString("name") ?: "",
                title = it.arguments?.getString("title") ?: "",
                content = it.arguments?.getString("content") ?: "",
            )

            commentScreen(navController = navController, post = post)
            //TutorDetailScreen(/*tutor = */navController=navController,name=name,gender=gender,locationGu=locationGu)
        }
    }
}
