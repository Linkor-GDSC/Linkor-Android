package com.gdsc.linkor.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun LinkorBottomNavigation(navController:NavController){
    BottomNavigation(
        backgroundColor = Color(0xFFFFFFFF)
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val items = listOf(
            BottomNavItem.TutorList,
            BottomNavItem.Learning,
            BottomNavItem.Community,
            BottomNavItem.MyPage
        )

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription =stringResource(id = item.title)
                    )
                       },
                //label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                alwaysShowLabel = false,
                selectedContentColor = Color(0xFF4C6ED7),
                unselectedContentColor = Color(0xFF4F4F4F),
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }

    }
}

@Preview
@Composable
fun Preview(){
    val navController = rememberNavController()
    LinkorBottomNavigation(navController = navController)
}